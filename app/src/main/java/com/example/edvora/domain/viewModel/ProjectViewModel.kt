//package com.example.edvora.domain.viewModel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.edvora.utils.LoadingState
//import kotlinx.coroutines.Dispatchers
//
//class ProjectViewModel {
//    private var apiType: String? = null
//    private val _loadingState = MutableLiveData<LoadingState>()
//    val loadingState: LiveData<LoadingState>
//        get() = _loadingState
//    fun getUserDetailApi() {
//        this.apiType = apiType
//        _loadingState.postValue(LoadingState.LOADING)
//
//        viewModelScope.launch(Dispatchers.IO) {
//            _privilegeResponseModel.postValue(
//                privilegeRepository.getPrivilegeDetails(
//                    authorization,
//                    apiKey,
//                    privilegeRequestModel
//                ).body()
//            )
//        }
//    }
//}