package hr.algebra.isstracker.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.isstracker.ASTRONAUT_CONTENT_URI
import hr.algebra.isstracker.ASTRONAUT_POSITION
import hr.algebra.isstracker.AstronautPagerActivity
import hr.algebra.isstracker.R
import hr.algebra.isstracker.framework.startActivity
import hr.algebra.isstracker.model.Astronaut
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AstronautAdapter (
    private val context: Context,
    private val items: MutableList<Astronaut>
) : RecyclerView.Adapter<AstronautAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvAstronautName = itemView.findViewById<TextView>(R.id.tvAstronautName)

        fun bind(astronaut: Astronaut) {
            tvAstronautName.text = astronaut.name
            Picasso.get()
                .load(File(astronaut.image))
                .resize(50, 50)
                .centerInside()
                .error(R.drawable.neutral_profile)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            context.startActivity<AstronautPagerActivity>(ASTRONAUT_POSITION, position)
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(R.string.really)
                setIcon(R.drawable.delete)
                setCancelable(true)
                setNegativeButton(R.string.cancel, null)
                setPositiveButton("OK") {_, _ -> deleteItem(position) }
                show()
            }
            true
        }
        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        CoroutineScope(Dispatchers.IO).launch {
            context.contentResolver.delete(
                ContentUris.withAppendedId(ASTRONAUT_CONTENT_URI, item._id!!),
                null,
                null
            )
        }

        File(item.image).delete()
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}