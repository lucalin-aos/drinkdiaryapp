package com.example.myapplication.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

val BorderColor = Color(0xFFE0D5C1) // 邊框顏色

/**
 * 新增飲品bottomSheetDialog
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddDrinkBottomSheet(onDismiss: () -> Unit) {
    // 狀態管理
    var name by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("冰") } // 預設選中冰
    var price by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("手搖") }

    val categories = listOf(
        stringResource(R.string.add_drink_type_hand),
        stringResource(R.string.add_drink_type_coffee),
        stringResource(R.string.add_drink_type_tea),
        stringResource(R.string.add_drink_type_water),
        stringResource(R.string.add_drink_type_juice),
        stringResource(R.string.add_drink_type_other)
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f) // 設定高度為螢幕 75%
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            stringResource(R.string.add_drink_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.main_color_darker)
        )

        // 飲品名稱
        InputSection(label = stringResource(R.string.add_drink_name_hint)) {
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "例如：冰美式咖啡"
            )
        }

        // 溫度選擇
        InputSection(label = stringResource(R.string.add_drink_temperature_hint)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SelectableButton(
                    text = "冰",
                    isSelected = temperature == stringResource(R.string.add_drink_temp_ice),
                    modifier = Modifier.weight(1f),
                    onClick = { temperature = "冰" }
                )
                SelectableButton(
                    text = "熱",
                    isSelected = temperature == stringResource(R.string.add_drink_temp_hot),
                    modifier = Modifier.weight(1f),
                    onClick = { temperature = "熱" }
                )
            }
        }

        // 價格與熱量 (並排)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InputSection(
                label = stringResource(R.string.add_drink_price),
                modifier = Modifier.weight(1f)
            ) {
                CustomTextField(
                    value = price,
                    onValueChange = { price = it },
                    placeholder = "$ 0"
                )
            }
            InputSection(
                label = stringResource(R.string.add_drink_kcal),
                modifier = Modifier.weight(1f)
            ) {
                CustomTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    placeholder = stringResource(R.string.add_drink_kcal_hint)
                )
            }
        }

        // 飲品類別 (兩列 Grid)
        InputSection(label = stringResource(R.string.add_drink_type)) {
            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categories.forEach { item ->
                    SelectableButton(
                        text = item,
                        isSelected = category == item,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.3f),
                        onClick = { category = item }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 確認按鈕
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.main_color_dark)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                stringResource(R.string.add_drink_confirm),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

// 輔助組件：標題與輸入框容器
@Composable
fun InputSection(label: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.main_color_darker)
        )
        content()
    }
}


// 輔助組件：自定義輸入框
@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colorResource(R.color.text_hint_color)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.white_light),
            unfocusedContainerColor = colorResource(R.color.white_light),
            disabledContainerColor = colorResource(R.color.white_light),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

// 輔助組件：可選取的按鈕 (冰熱/類別)
@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) colorResource(R.color.main_color_dark) else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, BorderColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = if (isSelected) Color.White else colorResource(R.color.main_color_darker),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}