package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val primary: Long,
    val onPrimary: Long,
    val secondary: Long,
    val onSecondary: Long,
    val tertiary: Long,
    val onTertiary: Long,
    val background: Long,
    val onBackground: Long,
)