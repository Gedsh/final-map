package pan.alexander.finalmap.ui.markers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pan.alexander.finalmap.R
import pan.alexander.finalmap.databinding.ItemMarkerBinding
import pan.alexander.finalmap.domain.entities.MapMarker
import java.util.*

class MarkersAdapter(
    private var onMarkerClickListener: (marker: MapMarker) -> Unit,
    private var onItemSwipedOutListener: (marker: MapMarker) -> Unit
) : RecyclerView.Adapter<MarkersAdapter.MarkersItemViewHolder>(), ItemTouchHelperAdapter {

    private val differ by lazy { AsyncListDiffer(this, diffCallback) }

    fun updateMarkers(data: List<MapMarker>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkersItemViewHolder {
        return MarkersItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_marker, parent, false)
        ).apply {
            itemView.setOnClickListener(this)
        }
    }

    override fun onBindViewHolder(holder: MarkersItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MarkersItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun bind(marker: MapMarker) = ItemMarkerBinding.bind(itemView).apply {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                itemMarkerLatValue.text = String.format(Locale.US, "%.2f", marker.lat)
                itemMarkerLonValue.text = String.format(Locale.US, "%.2f", marker.lon)

                marker.name.let {
                    itemMarkerNameValue.text = it
                    if (it.isNotEmpty()) {
                        itemMarkerNameTitle.visibility = View.VISIBLE
                        itemMarkerNameValue.visibility = View.VISIBLE
                    } else {
                        itemMarkerNameTitle.visibility = View.GONE
                        itemMarkerNameValue.visibility = View.GONE
                    }
                }

                marker.note.let {
                    itemMarkerDescriptionValue.text = it
                    if (it.isNotEmpty()) {
                        itemMarkerDescriptionTitle.visibility = View.VISIBLE
                        itemMarkerDescriptionValue.visibility = View.VISIBLE
                    } else {
                        itemMarkerDescriptionTitle.visibility = View.GONE
                        itemMarkerDescriptionValue.visibility = View.GONE
                    }
                }

            }
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onMarkerClickListener(differ.currentList[position])
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<MapMarker>() {
        override fun areItemsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean =
            oldItem == newItem

    }

    override fun onItemDismiss(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            onItemSwipedOutListener(differ.currentList[position])
        }
    }
}
