import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // --- Configuração Segura do Repositório do Mapbox ---
        maven {
            url = java.net.URI("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                // Usuário padrão do Mapbox para downloads
                username = "mapbox"

                // Lê a chave secreta 'sk' do arquivo local.properties
                val properties = Properties()
                val localPropertiesFile = settingsDir.resolve("local.properties")
                if (localPropertiesFile.exists()) {
                    properties.load(localPropertiesFile.inputStream())
                }
                password = properties.getProperty("MAPBOX_DOWNLOAD_TOKEN") ?: ""
            }
        }
    }
}

rootProject.name = "DriveZ"
include(":app")