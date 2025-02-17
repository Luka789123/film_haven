package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun  NavBarIcon(icon: ImageVector, label:String, isSelected:Boolean, onClickedCallBack:()-> Unit){
    return Box(
        modifier = if (isSelected) Modifier
            .clip(RoundedCornerShape(35.dp))
            .background(MaterialTheme.colorScheme.primary)
        else
            Modifier
                .clip(RoundedCornerShape(35.dp))
                .background(Color.Transparent)
    ){
        IconButton(
            onClick = onClickedCallBack,
            Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        ) {
            Icon(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                imageVector = icon,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                contentDescription = label)
        }
    }
}