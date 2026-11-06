# Litematic Printer Ling

[简体中文](README.md) | **English**

[![build](https://github.com/ling23323/Litematica-Printer-Ling/actions/workflows/build.yml/badge.svg)](https://github.com/ling23323/Litematica-Printer-Ling/actions/workflows/build.yml)
[![GitHub release](https://img.shields.io/github/v/release/ling23323/Litematica-Printer-Ling?include_prereleases&label=release)](https://github.com/ling23323/Litematica-Printer-Ling/releases)
[![License](https://img.shields.io/github/license/ling23323/Litematica-Printer-Ling)](LICENSE.md)

A client-side Fabric mod that adds automatic schematic printing to
[Litematica](https://modrinth.com/mod/litematica): it places the right blocks around the
player, and also provides fill, fluid draining, mining, bedrock breaking and container
syncing helpers.

This repository is a personally maintained fork of
[Litematica Printer](https://github.com/Yur1Ca/litematica-printer). The feature set is kept,
while the **public-server defaults, controls and GUI organisation** were reworked so the mod
works out of the box.

---

## What this fork changes

- **Server safe mode** — one switch in the *Core* tab swaps between a conservative
  public-server preset and a single-player performance preset; bind a hotkey to it.
  The three safe values (place interval, blocks per tick, breaks per tick) are configurable,
  and editing any rate value while it is on turns the preset off without overwriting your edit.
- **Working modes** — single mode (one feature at a time), multi mode (independent switches),
  and a new **Custom** single-mode entry that runs exactly the features you tick
  (e.g. print + mine + fill only).
- **Iteration direction** — mine / fill / cover / fluid / bedrock can start from the nearest
  block (default), bottom-up, top-down, or sweep along either horizontal axis.
- **Feedback** — missing-material HUD is on by default, per-block failure messages are
  throttled and only built when actually shown.
- **Tool switching** now belongs to Tweakeroo only; the built-in selector was removed so the
  two cannot fight over the hotbar. Bedrock breaking still picks its own pickaxe.

Default hotkeys: `Caps Lock` work switch, `Z,Y` settings, `Ctrl+G` stop all. The mode-cycle hotkey is unbound by default; bind it yourself if you use single mode.

---

## Supported Minecraft versions

1.18.2 · 1.19.4 · 1.20.1 · 1.20.2 · 1.20.4 · 1.20.6 · 1.21.1 · 1.21.3 · 1.21.4 · 1.21.5 ·
1.21.6 · 1.21.9 · 1.21.11 · 26.1 · 26.2 · 26.3

You can install the per-version jar (`litematic-printer-Ling-<mc>.jar`) or the all-in-one
wrapper (`litematic-printer-Ling-all.jar`).

## Download

The only official distribution channel is
**[GitHub Releases](https://github.com/ling23323/Litematica-Printer-Ling/releases)**:

- `litematic-printer-Ling-<mc>.jar` — per-version jar, pick the one matching your Minecraft
- `litematic-printer-Ling-all.jar` — all-in-one wrapper containing all 16 version jars

Automated builds are marked **Alpha**, manually published releases are **Beta**.
There is no stable channel and nothing is published to Modrinth.

## Requirements

Required: Fabric Loader, Fabric API, MaLiLib, Litematica.

Optional: Tweakeroo (auto tool switch, durability guard), Quick Shulker, Take It Out,
Chest Tracker, Neko-Carpet-Addition (server-side void warehouse, 1.21.11+).
All of them must match the Minecraft version.

---

## Building

**JDK 25 is required.** JDK 26/27 crashes Lombok 1.18.42
(`com.sun.tools.javac.tree.EndPosTable` was removed).

```bash
./gradlew buildAndCollect          # all versions -> build/libs/<mod_version>/<mc_version>/
./gradlew :26.3:buildAndCollect    # only 26.3
./gradlew :fabricWrapper:build     # all-in-one wrapper jar
```

On Windows you can run `build-all-versions.bat` instead (edit `JAVA_HOME` inside it if your
JDK 25 lives elsewhere).

The build runs unit tests, the `verifyArchitecture` boundary check and the JaCoCo coverage gate.

---

## Credits, provenance & license

This project is a **modified version** of earlier work; copyright remains with the original
authors:

- [aleksilassila/litematica-printer](https://github.com/aleksilassila/litematica-printer) (original author)
- [Yur1Ca/litematica-printer](https://github.com/Yur1Ca/litematica-printer) (Hana branch)
- [zhaixianyu](https://github.com/zhaixianyu), [BlinkWhite](https://github.com/BiliXWhite), [bunnyi116](https://github.com/bunnyi116)

Licensed under the **GNU Affero General Public License v3.0 (AGPL-3.0)** — see
[LICENSE.md](LICENSE.md). What was changed, who holds the original copyright and where to get
the source is documented in [NOTICE.md](NOTICE.md) (AGPL section 5 notice).

If you distribute this mod or a modified version you must:

1. license the whole work under AGPL-3.0 without extra restrictions;
2. provide the complete Corresponding Source;
3. keep the original attributions and notices, and state that you changed the work.

Every built jar embeds `META-INF/LICENSE.md` and `META-INF/NOTICE.md`.

Provided "as is", without warranty of any kind. This is a community fork; it does not represent
the upstream authors and is not endorsed by them.

