package pan.alexander.finalmap.ui.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pan.alexander.finalmap.databinding.DetailsFragmentBinding
import pan.alexander.finalmap.domain.entities.MapMarker
import pan.alexander.finalmap.ui.base.BaseFragment
import pan.alexander.finalmap.utils.app
import java.util.*
import javax.inject.Inject

private const val MARKER_ID_ARG = "markerId"

class DetailsFragment : BaseFragment<DetailsFragmentBinding>() {

    private val numberRegex by lazy { Regex("\\d+\\.?\\d+") }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: DetailsViewModel by viewModels(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMarkerId()?.let {
            observeMarkerChanges(it)
        }

        observeDeleteButtonOnClick()
    }

    override fun onPause() {
        super.onPause()

        getMarkerId()?.let {
            updateMarker(it)
        }
    }

    private fun getMarkerId() = arguments?.getInt(MARKER_ID_ARG)

    private fun observeMarkerChanges(markerId: Int) = with(binding) {
        viewModel.getMarkerById(markerId).observe(viewLifecycleOwner) {
            detailsLatValueEditText.setText(String.format(Locale.US, "%.2f", it.lat))
            detailsLonValueEditText.setText(String.format(Locale.US, "%.2f", it.lon))
            detailsNameEditText.setText(it.name)
            detailsDescriptionEditText.setText(it.note)
        }
    }

    private fun updateMarker(markerId: Int) = with(binding) {
        val lat = detailsLatValueEditText.text.toString().let {
            if (numberRegex.matches(it)) {
                it
            } else {
                ""
            }
        }

        val lon = detailsLonValueEditText.text.toString().let {
            if (numberRegex.matches(it)) {
                it
            } else {
                ""
            }
        }

        if (lat.isEmpty() || lon.isEmpty()) {
            return@with
        }

        val marker = MapMarker(
            id = markerId,
            lat = lat.toDouble(),
            lon = lon.toDouble(),
            name = detailsNameEditText.text.toString(),
            note = detailsDescriptionEditText.text.toString()
        )

        viewModel.updateMarker(marker)
    }

    private fun observeDeleteButtonOnClick() {
        binding.detailsDelFloatingButton.setOnClickListener {
            getMarkerId()?.let {
                deleteMarker(it)
            }
            findNavController().popBackStack()
        }
    }

    private fun deleteMarker(markerId: Int) {
        viewModel.deleteMarker(markerId)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DetailsFragmentBinding = DetailsFragmentBinding.inflate(
        inflater,
        container,
        false
    )

}
