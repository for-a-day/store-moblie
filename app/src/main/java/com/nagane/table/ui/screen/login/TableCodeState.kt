package com.nagane.table.ui.screen.login

import com.nagane.table.ui.screen.common.TextFieldState
import com.nagane.table.ui.screen.common.textFieldStateSaver


class TableCodeState(val tableCode: String? = null) :
    TextFieldState(
        validator = ::isTableCodeValid,
        errorFor = ::tableCodeValidationError
    ) {
    init {
        tableCode?.let {
            text = it
        }
    }
}

private fun tableCodeValidationError(tableCode: String) : String {
    return "최대 길이를 초과했습니다."
}

private fun isTableCodeValid(tableCode: String) : Boolean {
    return tableCode.length < 7
}

val TableCodeStateSaver = textFieldStateSaver(TableCodeState())