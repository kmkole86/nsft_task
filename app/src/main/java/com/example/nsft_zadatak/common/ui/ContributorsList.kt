package com.example.nsft_zadatak.common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.model.GitRepositoryContributorUiModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ContributorsListItem(
    contributor: GitRepositoryContributorUiModel,
    onFavouriteClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.spacing_10x)),
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.spacing_1x))),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.spacing_0_5x)
        )
    ) {
        Row(
            modifier = modifier.padding(all = dimensionResource(id = R.dimen.spacing_1x)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                model = contributor.avatarUrl,
                contentDescription = "icon",
                modifier = modifier
                    .aspectRatio(ratio = 1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.spacing_1x))),
            ) {
                it
                    .error(R.drawable.error)
                    .placeholder(R.drawable.image)
                    .load(contributor.avatarUrl)
            }
            Text(
                modifier = modifier
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.spacing_1x))
                    .align(alignment = Alignment.Top),
                text = contributor.name,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { onFavouriteClicked(contributor.id) },
                modifier
            ) {
                Icon(
                    if (contributor.isFavourite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    stringResource(R.string.favourite)
                )
            }
        }
    }
}