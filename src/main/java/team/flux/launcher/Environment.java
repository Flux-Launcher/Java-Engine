package team.flux.launcher;

import team.flux.launcher.model.LauncherVariables;
import team.flux.launcher.model.products.MojangProduct;
import team.flux.launcher.model.products.FluxProduct;

import java.io.File;

public class Environment {

    private File gameFolder, assetsFolder;
    private MojangProduct vanilla;
    private MojangProduct.Version target;
    private MojangProduct.Game game; // Vanilla / Optifine / Fabric / Forge
    private MojangProduct.Game inherited; // just Vanilla (is parent of modloader)
    private FluxProduct.Product flux;
    private LauncherVariables variables;

    public File getGameFolder() { return gameFolder; }
    public void setGameFolder(File gameFolder) { this.gameFolder = gameFolder; }

    public File getAssetsFolder() { return assetsFolder; }
    public void setAssetsFolder(File assetsFolder) { this.assetsFolder = assetsFolder; }

    public MojangProduct getVanilla() { return vanilla; }
    public void setVanilla(MojangProduct vanilla) { this.vanilla = vanilla; }

    public MojangProduct.Version getTarget() { return target; }
    public void setTarget(MojangProduct.Version target) { this.target = target; }

    public MojangProduct.Game getGame() { return game; }
    public void setGame(MojangProduct.Game game) { this.game = game; }

    public MojangProduct.Game getInherited() { return inherited; }
    public void setInherited(MojangProduct.Game inherited) { this.inherited = inherited; }

    public FluxProduct.Product getFlux() { return flux; }
    public void setFlux(FluxProduct.Product flux) { this.flux = flux; }

    public LauncherVariables getVariables() { return variables; }
    public void setVariables(LauncherVariables variables) { this.variables = variables; }
}
