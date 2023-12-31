package com.yosha10.pokepedia.utils

import androidx.compose.ui.graphics.Color
import com.yosha10.pokepedia.data.remote.responses.StatsItem
import com.yosha10.pokepedia.data.remote.responses.Type
import com.yosha10.pokepedia.data.remote.responses.TypesItem
import com.yosha10.pokepedia.ui.theme.*
import java.util.Locale

fun parseTypeToColor(type: TypesItem):  Color {
    return when(type.type.name?.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: StatsItem): Color {
    return when(stat.stat?.name?.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: StatsItem): String {
    return when(stat.stat?.name?.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}