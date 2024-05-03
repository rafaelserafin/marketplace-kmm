package domain.useCases

import Factory
import common.UseCase
import data.DesignSystemRepository
import data.DesignSystemRepositoryImpl
import data.api.DesignSystemApiImpl
import di.CoreContainer
import domain.entities.Theme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoadAppThemeUseCaseFactory : Factory<LoadAppThemeUseCase>() {
    override fun provide(): LoadAppThemeUseCase {
        return LoadAppThemeUseCase(
            DesignSystemRepositoryImpl(
                DesignSystemApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class LoadAppThemeUseCase(
    private val designSystemRepository: DesignSystemRepository,
) : UseCase<String, Theme>() {
    override suspend fun implementation(input: String): Theme {
        val localTheme = designSystemRepository.loadThemeLocal(input)

        coroutineScope {
            launch {
                // update local theme
                designSystemRepository.saveThemeLocal(input, designSystemRepository.getTheme(input))
            }
        }

        return localTheme ?: designSystemRepository.getTheme(input)
    }
}