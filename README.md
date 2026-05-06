🛡️ NetShield — Network Intrusion Detection System

NetShield est un système de détection d'intrusion réseau en temps réel développé en Java, capable de capturer et analyser le trafic réseau pour détecter des attaques telles que le SYN Flood, le Port Scan et les attaques DDoS.


📸 Aperçu
✅ Listening on interface: ens33
📦 [2026-05-06T14:25:55Z] TCP | 192.168.192.1:12345 → 192.168.192.129:80 | 54 bytes
📦 [2026-05-06T14:25:55Z] TCP | 192.168.192.1:12346 → 192.168.192.129:81 | 54 bytes
🚨 [PORT SCAN]  IP: 192.168.192.1 | 21 ports en 10s → [80, 81, 82, 443...]
🚨 [SYN FLOOD]  IP attaquante: 192.168.192.1 | 101 SYN/5s
🚨 [DDoS]       IP: 192.168.192.1 | 501 paquets UDP en 5s

🚀 Fonctionnalités
FonctionnalitéDescription📦 Capture de paquetsCapture en temps réel via pcap4j (TCP, UDP, ICMP)🔍 SYN Flood DetectionDétecte +100 SYN sans ACK en 5 secondes🔍 Port Scan DetectionDétecte +20 ports scannés en 10 secondes🔍 DDoS DetectionDétecte +500 paquets/IP en 5 secondes🌐 Multi-protocolesSupporte TCP, UDP, ICMP, et autres⏱️ Temps réelAnalyse et alertes instantanées

🏗️ Architecture du projet
NetShield/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── netshield/
                    ├── Main.java                        # Point d'entrée
                    ├── capture/
                    │   ├── PacketSniffer.java           # Capture réseau
                    │   └── PacketInfo.java              # Modèle de paquet
                    └── detection/
                        ├── ThreatDetector.java          # Orchestrateur
                        ├── SynFloodDetector.java        # Détection SYN Flood
                        ├── PortScanDetector.java        # Détection Port Scan
                        └── DDoSDetector.java            # Détection DDoS

⚙️ Prérequis

Java 17+
Maven 3.x
libpcap installé sur Linux
Droits root (nécessaire pour capturer les paquets réseau)

bash# Installer libpcap sur Ubuntu/Debian
sudo apt install libpcap-dev -y

📦 Installation
bash# 1. Cloner le projet
git clone https://github.com/ton-username/NetShield.git
cd NetShield

# 2. Compiler
mvn compile

# 3. Lancer (avec sudo obligatoire)
sudo mvn exec:java -Dexec.mainClass="com.netshield.Main"

🧪 Tester les détections
Ouvre 2 terminaux sur ta machine :
Terminal 1 — Lance le sniffer :
bashsudo mvn compile exec:java -Dexec.mainClass="com.netshield.Main"
Terminal 2 — Lance les attaques :
bash# Installer les outils de test
sudo apt install nmap hping3 -y

# Test Port Scan
sudo nmap -sS -p 1-500 192.168.192.129

# Test SYN Flood
sudo hping3 -S --flood -p 80 192.168.192.129

# Test DDoS UDP
sudo hping3 --udp --flood 192.168.192.129

📊 Seuils de détection
AttaqueSeuilFenêtre de tempsSYN Flood> 100 paquets SYN5 secondesPort Scan> 20 ports différents10 secondesDDoS> 500 paquets5 secondes

Ces seuils sont configurables directement dans chaque fichier de détection.


🔧 Dépendances
xml<!-- pcap4j - Capture réseau -->
<dependency>
    <groupId>org.pcap4j</groupId>
    <artifactId>pcap4j-core</artifactId>
    <version>1.8.2</version>
</dependency>

<!-- pcap4j - Factory statique -->
<dependency>
    <groupId>org.pcap4j</groupId>
    <artifactId>pcap4j-packetfactory-static</artifactId>
    <version>1.8.2</version>
</dependency>

👩‍💻 Auteur
Hafsa — Projet de cybersécurité réseau
📧 Contact : [elidrissihafsa49@gmail.com]
🔗 GitHub : github.com/hfs-ch

📄 Licence
Ce projet est sous licence MIT — voir le fichier LICENSE pour plus de détails.
