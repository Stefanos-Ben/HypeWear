package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.core.domain.utils.toHex

@Composable
fun ProfileStep(
    state: ApparelFormState,
    onAction: (ApparelFormAction) -> Unit
    ) {

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    var showPicker by remember { mutableStateOf(false) }
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { onAction(ApparelFormAction.OnImagePicked(it)) }}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "STEP 1: BASIC INFO",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = { onAction(ApparelFormAction.OnFieldChanged("description", it)) },
            label = { Text("Description") },
            isError = state.fieldErrors["description"] != null,
            minLines = 3,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )
        Text(
            state.fieldErrors["description"].orEmpty(),
            color = MaterialTheme.colorScheme.error
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Image",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
        ) {
            when {
                state.imageUri != null ->
                    AsyncImage(
                        model =  state.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                state.imageUrl.isNotBlank() ->
                    AsyncImage(
                        model = state.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                else ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_photo_24),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Tap to upload a photo",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

            }
            if (state.isImageUploading)
                CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        Text(
            state.fieldErrors["image"].orEmpty(),
            color = MaterialTheme.colorScheme.error
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Color",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showPicker = true }
                .padding(vertical = 8.dp)
        ){
            val previewColor = runCatching { Color(state.color.toColorInt()) }
                .getOrDefault(Color.Transparent)

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(previewColor, CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )

            Spacer(Modifier.width(12.dp))
            Text(
                text = state.color.ifBlank { "Tap to choose" },
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            state.fieldErrors["colors"].orEmpty(),
            color = MaterialTheme.colorScheme.error
        )

        Spacer(Modifier.height(24.dp))
    }

    if (showPicker) {
        Dialog(onDismissRequest = { showPicker = false }) {
            val controller = rememberColorPickerController()
            Column(modifier = Modifier.padding(24.dp)) {
                HsvColorPicker(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    controller = controller
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showPicker = false }) { Text("Cancel") }
                    Spacer(Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            val colorHex = controller.selectedColor.value.toHex()
                            onAction(ApparelFormAction.OnFieldChanged("color", colorHex))
                            showPicker = false
                        }) { Text("Select") }
                }
            }
        }
    }
}