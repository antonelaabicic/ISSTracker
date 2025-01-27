package hr.algebra.isstracker.adapter

import android.content.ContentUris
import android.content.ContentValues
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
import hr.algebra.isstracker.R
import hr.algebra.isstracker.model.Astronaut
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

class AstronautPagerAdapter (
    private val context: Context,
    private val items: MutableList<Astronaut>
) : RecyclerView.Adapter<AstronautPagerAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val ivFav = itemView.findViewById<ImageView>(R.id.ivFav)

        private val tvAstronautName = itemView.findViewById<TextView>(R.id.tvAstronautName)
        private val tvCountry = itemView.findViewById<TextView>(R.id.tvCountry)
        private val tvAgency = itemView.findViewById<TextView>(R.id.tvAgency)
        private val tvDaysInSpace = itemView.findViewById<TextView>(R.id.tvDaysInSpace)
        private val tvPosition = itemView.findViewById<TextView>(R.id.tvPosition)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

        fun bind(astronaut: Astronaut) {
            tvAstronautName.text = astronaut.name
            tvCountry.text = itemView.context.getString(R.string.country_with_flag, astronaut.country, astronaut.flagCode)
            tvAgency.text = astronaut.agency
            tvDaysInSpace.text = String.format(Locale.getDefault(), "%.2f", astronaut.daysInSpace)
            tvPosition.text = astronaut.position
            tvDescription.text = astronaut.description
            Picasso.get()
                .load(File(astronaut.image))
                .resize(500, 500)
                .centerInside()
                .error(R.drawable.neutral_profile)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivImage)
            ivFav.setImageResource(if (astronaut.isFavorite) R.drawable.full_heart else R.drawable.empty_heart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.astronaut_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.ivFav.setOnLongClickListener {
            item.isFavorite = !item.isFavorite

            CoroutineScope(Dispatchers.IO).launch {
                context.contentResolver.update(
                    ContentUris.withAppendedId(ASTRONAUT_CONTENT_URI, item._id!!),
                    ContentValues().apply {
                        put(Astronaut::isFavorite.name, item.isFavorite)
                    },
                    null,
                    null
                )
            }
            notifyItemChanged(position)
            true
        }
        holder.bind(item)
    }
}
