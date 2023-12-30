pluginManagement {
    repositories {
        google();
        mavenCentral();
        //maven ( url ="https://jitpack.io" );

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        //google();
        //mavenCentral();
        google();
        mavenCentral();
        maven ( url ="https://jitpack.io" );
        gradlePluginPortal();
    }
}

rootProject.name = "ECommerce"
include(":app")
include(":admin_app")
