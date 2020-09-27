package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetStatsByDatesUseCase
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.PrefRepository
import java.util.*

class StatsViewModel(
    private val prefRepository: PrefRepository,
    private val getStatsByDatesUseCase: GetStatsByDatesUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val successResult = MutableLiveData<List<Task>>()

    internal fun getStatsByDate(firstDate: Date?, secondDate: Date?) {
        isViewLoading.value = true
        val params = GetStatsByDatesUseCase.Params(firstDate, secondDate)
        getStatsByDatesUseCase(params) {
            it.onSuccess {
                val ttt = it
                val rrr = ttt.size
                successResult.value = it
                isViewLoading.value = false
            }.onFailure {
                warningResult.value = it.message
                isViewLoading.value = false
            }
        }
    }

    internal fun saveDateRangeType(value: DateRangeType) = prefRepository.setStatsRange(value)

    internal fun getDateRangeType(): DateRangeType = prefRepository.getStatsRange() ?: DateRangeType.DAY

}