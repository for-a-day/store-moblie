/*
package com.nagane.table.ui.screen.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

*/
/** textField 상태 확인 코드 => jetsurvey 참고 *//*

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor : (String) -> String = { "" }
) {
    // 현재 텍스트
    var text: String by mutableStateOf("")
    // 현재 포커스 가지고 있는지
    var isFocused: Boolean by mutableStateOf(false)
    // 한번이라도 포커스를 받은 적이 있는지
    var isFocusedDirty: Boolean by mutableStateOf(false)
    // 오류를 표시할지
    private var displayErrors: Boolean by mutableStateOf(false)

    // 텍스트 유효한지 검증
    // 상속 또는 override 가능
    open val isValid: Boolean
        get() = validator(text)

    // 포커스 상태 변경
    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    // 오류 메시지 표시
    fun enableShowErrors() {
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text)
        } else {
            null
        }
    }
}

// textField 상태를 저장하고 복원하는 데 사용
fun textFieldStateSaver(
    // listSaver로 상태 저장 및 복원
    state: TextFieldState) = listSaver<TextFieldState, Any>(
    // 현재 상태 리스트로 변환하여 저장
    save = { listOf(it.text, it.isFocusedDirty)},
    // 저장된 리스트를 사용하여 상태 복원
    restore = {
        state.apply {
            text = it[0] as String
            isFocusedDirty = it[1] as Boolean
        }
    }
)*/
