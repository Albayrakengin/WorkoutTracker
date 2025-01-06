pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeTemplate"
include(":app")
include(":feature")
include(":core")
include(":core:data")
include(":core:designsystem")
include(":core:ui")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:auth")
include(":feature:home")
include(":feature:profile")
include(":feature:settings")
include(":feature:detail")
include(":feature:startworkout")
include(":feature:auth")
include(":core:domain")
