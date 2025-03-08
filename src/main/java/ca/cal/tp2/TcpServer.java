package ca.cal.tp2;

import org.h2.tools.Server;
import java.sql.SQLException;

public class TcpServer {
    private static Server tcpServer;

    public static void startTcpServer() throws SQLException {
        if (tcpServer != null && tcpServer.isRunning(true)) {
            System.out.println("⚠️ Serveur H2 TCP déjà en cours d'exécution.");
            return;
        }

        // Création et démarrage du serveur TCP H2
        tcpServer = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();

        if (tcpServer.isRunning(true)) {
            System.out.println("✅ Serveur H2 démarré avec succès !");
            System.out.println("🔗 JDBC URL : jdbc:h2:tcp://localhost:9092/mem:tp2amine;DB_CLOSE_DELAY=-1");
        } else {
            System.err.println("❌ Échec du démarrage du serveur H2 TCP.");
        }
    }

    public static void stopTcpServer() {
        if (tcpServer != null) {
            System.out.println("⏳ Arrêt du serveur H2...");
            tcpServer.stop();
            System.out.println("✅ Serveur H2 arrêté avec succès !");
            tcpServer = null;
        } else {
            System.out.println("⚠️ Aucun serveur H2 en cours d'exécution.");
        }
    }
}
