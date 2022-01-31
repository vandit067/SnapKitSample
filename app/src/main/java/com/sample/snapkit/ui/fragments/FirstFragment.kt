package com.sample.snapkit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.snapkit.data.api.SnapKitApi
import com.sample.snapkit.data.repository.SnapKitRepository
import com.sample.snapkit.databinding.FragmentFirstBinding
import com.sample.snapkit.ui.viewmodel.SnapKitViewModel
import org.json.JSONObject
import timber.log.Timber


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val snapKitApi: SnapKitApi by lazy { SnapKitApi.create() }
    private val snapKitRepository: SnapKitRepository by lazy { SnapKitRepository(snapKitApi = snapKitApi) }
    private val snapKitViewModel: SnapKitViewModel by lazy { ViewModelProvider(this, factory).get(SnapKitViewModel::class.java) }
    @Suppress("UNCHECKED_CAST")
    private var factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SnapKitViewModel(snapKitRepository = snapKitRepository) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snapKitViewModel.bitmojiList.observe(viewLifecycleOwner) { bitmojiItems ->
            Timber.v("Response bitmoji: $bitmojiItems")
        }

        snapKitViewModel.gifList.observe(viewLifecycleOwner) { gifItems ->
            Timber.v("Response bitmoji: $gifItems")
        }

        snapKitViewModel.stickerList.observe(viewLifecycleOwner) { stickerItems ->
            Timber.v("Response bitmoji: $stickerItems")
        }
        binding.buttonFirst.setOnClickListener {
            snapKitViewModel.getStickersBySearchTerm(createBody(search = "haha"))
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun createBody(search: String): String {
        return JSONObject().put("query", "query {\n" +
                "  sticker {\n" +
                "    searchStickers(\n" +
                "      req: {searchStickersParams: {searchText: \"$search\"}, stickerUserContext: {}}\n" +
                "    ) {\n" +
                "      stickerResults {\n" +
                "        items {\n" +
                "          pngURL\n" +
                "          id\n" +
                "          itemType\n" +
                "          thumbnailURL\n" +
                "        }\n" +
                "      }\n" +
                "      gifResults {\n" +
                "        items {\n" +
                "          mp4URL\n" +
                "          id\n" +
                "          itemType\n" +
                "          thumbnailGifURL\n" +
                "          gifURL\n" +
                "        }\n" +
                "      }\n" +
                "      bitmojiResults {\n" +
                "        items {\n" +
                "          id\n" +
                "          itemType\n" +
                "          webpLargeURL\n" +
                "          webpMediumURL\n" +
                "          webpThumbnailURL\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}").toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}