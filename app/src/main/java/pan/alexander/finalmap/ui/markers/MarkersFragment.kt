package pan.alexander.finalmap.ui.markers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import pan.alexander.finalmap.R
import pan.alexander.finalmap.databinding.MarkersFragmentBinding
import pan.alexander.finalmap.domain.entities.MapMarker
import pan.alexander.finalmap.ui.base.BaseFragment
import pan.alexander.finalmap.ui.markers.adapter.ItemTouchHelperCallback
import pan.alexander.finalmap.ui.markers.adapter.MarkersAdapter
import pan.alexander.finalmap.utils.app
import javax.inject.Inject

class MarkersFragment : BaseFragment<MarkersFragmentBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: MarkersViewModel by viewModels(factoryProducer = { factory })

    private val adapter by lazy {
        MarkersAdapter(::onMarkerItemClicked, ::onMarkerItemSwiped)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        observeMarkers()
    }

    private fun initRecycler() {
        (binding.markersRecycler.itemAnimator as SimpleItemAnimator)
            .supportsChangeAnimations = false
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).apply {
            attachToRecyclerView(binding.markersRecycler)
        }
        binding.markersRecycler.adapter = adapter
    }

    private fun observeMarkers() {
        viewModel.getAllMarkers().observe(viewLifecycleOwner) {
            adapter.updateMarkers(it)
        }
    }

    private fun onMarkerItemClicked(marker: MapMarker) {
        findNavController().navigate(
            R.id.navigate_to_marker_details,
            bundleOf("markerId" to marker.id)
        )
    }

    private fun onMarkerItemSwiped(marker: MapMarker) {
        viewModel.deleteMarker(marker.id)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MarkersFragmentBinding = MarkersFragmentBinding.inflate(
        inflater,
        container,
        false
    )
}
