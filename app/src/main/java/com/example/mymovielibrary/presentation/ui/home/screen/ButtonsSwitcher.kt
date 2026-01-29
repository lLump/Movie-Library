package com.example.mymovielibrary.presentation.ui.home.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout

@OptIn(ExperimentalMotionApi::class)
@Composable
fun AnimatedButtonsSwitcher(
    width: Dp,  // 150
    height: Dp, // 36 default
) {
    var animateToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(1000),
        label = "MotionLayout2Example"
    )
    MotionLayout(
        ConstraintSet(
            """ {
                backgroundSwitch: { 
                    start: ['parent', 'start', 36],
                    top: ['description', 'bottom', 16],
                    end: ['parent', 'end', 36],
                    custom: {
                      color: "#d2d2d2"
                    }
                },
                buttonSwitch: { 
                    top: ['backgroundSwitch', 'top', 0],
                    start: ['backgroundSwitch', 'start', 0]
                },
                light: { 
                    top: ['backgroundSwitch', 'top', 0],
                    start: ['backgroundSwitch', 'start', 0],
                    bottom: ['backgroundSwitch', 'bottom', 0]
                },
                dark: { 
                    top: ['backgroundSwitch', 'top', 0],
                    end: ['backgroundSwitch', 'end', 0],
                    bottom: ['backgroundSwitch', 'bottom', 0]
                }
             }"""
        ),
        ConstraintSet(
            """ {
                backgroundSwitch: { 
                    start: ['parent', 'start', 36],
                    top: ['description', 'bottom', 16],
                    end: ['parent', 'end', 36],
                    custom: {
                      color: "#343434"
                    }
                },
                buttonSwitch: { 
                    top: ['backgroundSwitch', 'top', 0],
                    end: ['backgroundSwitch', 'end', 0]
                },
                light: { 
                    top: ['backgroundSwitch', 'top', 0],
                    start: ['backgroundSwitch', 'start', 0],
                    bottom: ['backgroundSwitch', 'bottom', 0]
                },
                dark: { 
                    top: ['backgroundSwitch', 'top', 0],
                    end: ['backgroundSwitch', 'end', 0],
                    bottom: ['backgroundSwitch', 'bottom', 0]
                }
              }"""
        ),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("backgroundSwitch")
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = { animateToEnd = !animateToEnd })
                .background(customProperties("backgroundSwitch").color("color"))
        )

        Box(
            modifier = Modifier
                .layoutId("buttonSwitch")
                .width(width / 2)
                .height(height)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray)
        )

        Text(
            text = "DAY",
            modifier = Modifier
                .layoutId("light")
                .width(width / 2),
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "WEEK",
            modifier = Modifier
                .layoutId("dark")
                .width(width / 2),
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SingleChoiceButtons(
    modifier: Modifier,
    onChoice: (Int) -> Unit,
    firstTextId: Int,
    secondTextId: Int
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(bottomStart = 24.dp, topStart = 24.dp),
            onClick = {
                selectedIndex = 0
                onChoice(selectedIndex)
            },
            selected = 0 == selectedIndex,
            icon = {},
        ) {
            Text(text = stringResource(id = firstTextId))
        }
        SegmentedButton(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(0.dp),
            onClick = {
                selectedIndex = 1
                onChoice(selectedIndex)
            },
            selected = 1 == selectedIndex,
            icon = {},
        ) {
            Text(text = stringResource(id = secondTextId))
        }
    }
}