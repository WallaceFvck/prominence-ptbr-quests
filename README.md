# Prominence II: Hasturian Era - Tradução PT-BR das Quests

<p align="center">
  <img src="https://i.imgur.com/ZgOuvRC.png" width="600"/>
</p>
<p align="center"><em>Tradução completa das quests do Prominence II: Hasturian Era para Português do Brasil</em></p>

---

## O que é isso?

Um mod Fabric que instala automaticamente a tradução PT-BR de todas as **33 quests** do modpack [Prominence II: Hasturian Era](https://www.curseforge.com/minecraft/modpacks/prominence-2-hasturian-era).
> Mod não-oficial, sem afiliação com os criadores do Prominence II ou do FTB Quests.

Basta colocar o `.jar` na pasta `mods/` e abrir o jogo - sem precisar substituir arquivos manualmente.

---

## Instalação

1. Baixe o `.jar` na aba [Releases](../../releases/latest)
2. Coloque na pasta `mods/` da sua instância do Prominence II no CurseForge
3. Abra o jogo - a tradução é instalada automaticamente na primeira inicialização

> **Compatível com:** Prominence II: Hasturian Era · Minecraft 1.20.1 · Fabric

---

## Como funciona

Na primeira vez que o jogo abre com o mod:

- Faz **backup automático** dos arquivos originais em `config/ftbquests/quests_backup_DATA_HORA`
- Copia os **48 arquivos `.snbt`** traduzidos para `config/ftbquests/quests/`
- Cria um arquivo `prominence_ptbr_installed.txt` na raiz da instância para não reinstalar nas próximas sessões

> Para reinstalar (ex: após update do modpack), delete o arquivo `prominence_ptbr_installed.txt` na pasta da instância.

---

## Capítulos traduzidos

<details>
<summary>Ver lista completa (33 capítulos)</summary>

- Applied Energistics 2
- Archon
- Artifact Reforging
- A Jolly Christmas
- Botania
- Bewitchment
- Collectibles & Collectibles 2
- Create
- Deeper and Darker
- Enchanting
- Getting Started
- Gadgets
- Gear - Marium's Soulslike Weaponry
- Hasturian Era
- Industrial Revolution
- Information & New Mechanics
- Main Story
- Modern Industrialization
- Planets and Space
- Powah
- Reforging
- Simply Swords
- Spell Books
- Tech Reborn
- The Bumblezone
- The Nether
- The Scarred Night
- To the End
- Trophy Collection
- The Prison
- The Art of Transmogrification
- Mastering Fate

</details>

---

## Compilar do código fonte

**Requisitos:** Java 17 ou superior

```powershell
git clone https://github.com/WallaceFvck/prominence-ptbr-quests.git
cd prominence-ptbr-quests
.\gradlew.bat build
```

O `.jar` compilado estará em `build/libs/prominence-ptbr-quests-1.0.0.jar`.

> A primeira compilação baixa ~500MB de dependências (Fabric, mappings). Compilações seguintes são rápidas.

---

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

Este mod é não-oficial e não possui afiliação com os criadores do Prominence II ou do FTB Quests.
