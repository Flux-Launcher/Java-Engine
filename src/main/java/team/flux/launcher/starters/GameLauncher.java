package team.flux.launcher.starters;

import team.flux.launcher.model.products.MojangProduct;
import team.flux.launcher.starters.impl.ClassloaderLauncher;
import team.flux.launcher.starters.impl.ClasspathLauncher;

import java.net.URL;
import java.util.List;

public class GameLauncher {

    public enum LaunchMode {
        ClassLoader, ClassPath
    }

    private final ILibraryManager launcher;

    public GameLauncher(MojangProduct.Game game, MojangProduct.Game vanilla, List<URL> libraries, LaunchMode mode, boolean startOnFirstThread) {
        switch (mode) {
            case ClassLoader:
                this.launcher = new ClassloaderLauncher(game, libraries);
                break;
            case ClassPath:
                this.launcher = new ClasspathLauncher(game, vanilla, libraries, startOnFirstThread);
                break;
            default:
                throw new IllegalArgumentException("Unknown launch mode");
        }
    }

    public void launch(List<String> args) throws Exception {
        launcher.launch(args);
    }
}
