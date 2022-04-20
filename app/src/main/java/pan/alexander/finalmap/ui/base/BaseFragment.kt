package pan.alexander.finalmap.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding> : Fragment() {

    private var _binding: V? = null

    protected val binding: V get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getFragmentBinding(inflater, container).also {
        _binding = it
    }.root

    protected abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): V

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
