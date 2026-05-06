 🛡️ NetShield — Network Intrusion Detection System

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square&logo=apachemaven)
![pcap4j](https://img.shields.io/badge/pcap4j-1.8.2-blue?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Platform](https://img.shields.io/badge/Platform-Linux-yellow?style=flat-square&logo=linux)

> **NetShield** est un système de détection d'intrusion réseau (**NIDS**) développé en Java.  
> Il capture le trafic réseau en temps réel et détecte les attaques : **SYN Flood**, **Port Scan** et **DDoS**.

---
 📸 Démonstration

```
=== Available Network Interfaces ===
  → ens33 | IP: /192.168.192.129

✅ Listening on interface: ens33

📦 [2026-05-06T14:25:55Z] TCP  | 192.168.192.1:12345 → 192.168.192.129:80  | 54 bytes
📦 [2026-05-06T14:25:55Z] TCP  | 192.168.192.1:12346 → 192.168.192.129:81  | 54 bytes
📦 [2026-05-06T14:25:55Z] ICMP | 192.168.192.1:0     → 192.168.192.129:0   | 74 bytes

🚨 [PORT SCAN DÉTECTÉ] IP: 192.168.192.1 | 21 ports en 10s → [80, 81, 443...]
🚨 [SYN FLOOD DÉTECTÉ] IP: 192.168.192.1 | 101 SYN/5s
🚨 [DDoS DÉTECTÉ]      IP: 192.168.192.1 | 501 paquets UDP en 5s

🛑 Sniffer stopped.
✅ Capture complete.
```

---

## ✨ Fonctionnalités

| Module | Description |
|---|---|
| 📦 **Capture de paquets** | Capture en temps réel via `pcap4j` (TCP, UDP, ICMP) |
| 🚨 **SYN Flood Detection** | Détecte > 100 paquets SYN sans ACK en 5 secondes |
| 🚨 **Port Scan Detection** | Détecte > 20 ports différents scannés en 10 secondes |
| 🚨 **DDoS Detection** | Détecte > 500 paquets par IP en 5 secondes |
| 🌐 **Multi-protocoles** | TCP, UDP, ICMP, et autres |
| ⚡ **Temps réel** | Alertes instantanées dès la détection |

---

## 🏗️ Architecture du projet

```
NetShield/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── netshield/
                    ├── Main.java                    # Point d'entrée
                    ├── capture/
                    │   ├── PacketSniffer.java        # Capture réseau (pcap4j)
                    │   └── PacketInfo.java           # Modèle de données paquet
                    └── detection/
                        ├── ThreatDetector.java       # Orchestrateur des détections
                        ├── SynFloodDetector.java     # Détection SYN Flood
                        ├── PortScanDetector.java     # Détection Port Scan
                        └── DDoSDetector.java         # Détection DDoS / Flood
```

---

## ⚙️ Prérequis

| Outil | Version |
|---|---|
| Java | 17+ |
| Maven | 3.x |
| libpcap | Dernière version |
| OS | Linux (Ubuntu recommandé) |

```bash
# Installer libpcap
sudo apt install libpcap-dev -y

# Installer nmap et hping3 pour les tests
sudo apt install nmap hping3 -y
```

---

## 🚀 Installation & Lancement

```bash
# 1. Cloner le projet
git clone https://github.com/hfs-ch/NetShield.git
cd NetShield

# 2. Compiler le projet
mvn compile

# 3. Lancer le sniffer (sudo obligatoire pour capturer les paquets)
sudo mvn exec:java -Dexec.mainClass="com.netshield.Main"
```

---

## 🧪 Tester les détections

Lance le sniffer dans un **Terminal 1**, puis les attaques dans un **Terminal 2** :

### Terminal 1 — Sniffer
```bash
cd ~/NetShield
sudo mvn compile exec:java -Dexec.mainClass="com.netshield.Main"
```

### Terminal 2 — Attaques simulées
```bash
# 🔍 Test Port Scan (déclenche PortScanDetector)
sudo nmap -sS -p 1-500 192.168.192.129

# 💥 Test SYN Flood (déclenche SynFloodDetector)
sudo hping3 -S --flood -p 80 192.168.192.129

# 🌊 Test UDP Flood (déclenche DDoSDetector)
sudo hping3 --udp --flood 192.168.192.129
```

---

## 📊 Seuils de détection configurables

| Attaque | Seuil | Fenêtre |
|---|---|---|
| SYN Flood | > 100 paquets SYN | 5 secondes |
| Port Scan | > 20 ports différents | 10 secondes |
| DDoS | > 500 paquets | 5 secondes |

> 💡 Ces seuils sont modifiables dans chaque fichier de détection.

---

## 🔧 Dépendances Maven

```xml
<!-- Capture réseau -->
<dependency>
    <groupId>org.pcap4j</groupId>
    <artifactId>pcap4j-core</artifactId>
    <version>1.8.2</version>
</dependency>

<!-- Factory statique pcap4j -->
<dependency>
    <groupId>org.pcap4j</groupId>
    <artifactId>pcap4j-packetfactory-static</artifactId>
    <version>1.8.2</version>
</dependency>
```

---

## 🗺️ Roadmap

- [x] Capture de paquets réseau en temps réel
- [x] Détection SYN Flood
- [x] Détection Port Scan
- [x] Détection DDoS / UDP Flood
- [ ] Interface graphique (Dashboard)
- [ ] Export des logs en fichier CSV / JSON
- [ ] Blocage automatique des IP suspectes
- [ ] Notifications par email en cas d'alerte

---

## 👩‍💻 Auteure

**Hafsa El Idrissi**  
📧 [elidrissihafsa49@gmail.com](mailto:elidrissihafsa49@gmail.com)  
🔗 [github.com/hfs-ch](https://github.com/hfs-ch)

---

## 📄 Licence

Ce projet est sous licence **MIT** — voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

> ⚠️ **Avertissement légal** : NetShield est développé à des fins **éducatives** uniquement.  
> N'utilisez jamais ces outils sur des réseaux sans autorisation explicite du propriétaire.
