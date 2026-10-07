package team.flux.launcher.instance;

import com.google.gson.Gson;
import team.flux.launcher.Launcher;
import team.flux.launcher.Main;
import team.flux.launcher.logging.MyLogger;
import team.flux.launcher.model.LauncherVariables;
import team.flux.launcher.model.products.FluxProduct;
import team.flux.launcher.utils.Utils;

import java.io.IOException;
import java.net.URL;

public class Flux {

    private static final MyLogger log = new MyLogger(Flux.class);
    private String version;
    private boolean startonfirstthread;

    public Flux(String version, boolean startonfirstthread) {
        this.version = version;
        this.startonfirstthread = startonfirstthread;
    }

    public void prepareLaunch(String gamePath) throws Exception {
        String baseUrl = String.format("%s/downloads/flux-lite", Main.getFluxAPI());
        String indexUrl = String.format("%s/index.json", baseUrl);

        Gson gson = new Gson();
        String jsonResponse;
        try {
            jsonResponse = Utils.makeGetRequest(new URL(indexUrl));
        } catch (IOException e) {
            log.error("Could not reach flux website", e);
            return;
        }
        FluxProduct prods = gson.fromJson(jsonResponse, FluxProduct.class);

        String[] split = version.split("-");

        FluxProduct.Product fluxProduct = null;
        for (FluxProduct.Product prod : prods.products) {
            if (prod.gameversion.equals(split[1])) {
                fluxProduct = prod;
                break;
            }
        }
        if (fluxProduct == null) {
            log.error("Could not find flux version: " + version);
            return;
        }

        log.info(String.format("Launching flux instance (%s)", fluxProduct.gameversion));
        new Launcher(new LauncherVariables(fluxProduct.gameversion, gamePath, startonfirstthread), fluxProduct).launchGame();
    }
}
