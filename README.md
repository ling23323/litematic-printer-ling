# Litematic Printer Ling

**简体中文** | [English](README.en.md)

[![build](https://github.com/ling23323/Litematica-Printer-Ling/actions/workflows/build.yml/badge.svg)](https://github.com/ling23323/Litematica-Printer-Ling/actions/workflows/build.yml)
[![GitHub release](https://img.shields.io/github/v/release/ling23323/Litematica-Printer-Ling?include_prereleases&label=release)](https://github.com/ling23323/Litematica-Printer-Ling/releases)
[![License](https://img.shields.io/github/license/ling23323/Litematica-Printer-Ling)](LICENSE.md)

一个客户端 Fabric 模组，为 [Litematica](https://modrinth.com/mod/litematica) 补上自动还原投影的能力：
在玩家周围自动放置投影里的方块，并提供填充、排流体、挖掘、破基岩、容器同步等辅助功能。

本仓库是基于 [Litematica Printer](https://github.com/Yur1Ca/litematica-printer) 分支的个人维护版本，
在保留原有功能的前提下重做了**公共服默认参数、操作入口和界面组织**，目标是一个"装好就能用"的版本。

---

## 本版做了什么

### 默认参数直接面向服服务器

- **安全模式**（核心页签第一项）：一个开关打包两套速率预设，绑个热键即可在"保守档 / 性能档"之间切换。
- 保守档的三项数值（放置间隔、每刻放置数、每刻挖掘数）**可以自定义**，不是写死的。
- 开着安全模式时手动改动任一速率项，会自动退出安全模式并保留你改的值。
- 缺材料 HUD 默认开启；逐方块失败提示做了节流，action bar 不再每 tick 闪烁。

### 覆盖打印（本次补全）

`printReplace` + `printReplaceableList` 之前只出现在配置界面里、实际不生效。现在名单内的方块会被
当作「可以直接放上去」：既不跳过，也不先破坏。名单支持方块 ID、标签 `#tag`、中文名/拼音，
以及 `[状态]` 谓词，改完立即生效。

### 操作入口

| 功能 | 默认热键 |
| --- | --- |
| 工作开关 | `Caps Lock` |
| 切换模式 / 工作类型 | 未绑定，需自行设置（单模式循环全部功能；自定义模式只循环你勾选的功能） |
| 打开设置 | `Z,Y` |
| 关闭全部模式 | `左 Ctrl + G` |

### 工作模式

- **单模式**：同一时间只跑一种功能，用「切换模式」热键循环全部功能。
- **多模式**：每个功能独立开关，可同时开启。
- **自定义**：在功能页签里勾选你要用的几个功能，之后「切换模式」热键只会在这几个里循环，
  等价于「把单模式的循环列表换成你自己的子集」。进入自定义模式时若当前工作类型没被勾选，
  会自动跳到第一个勾选的功能。

### 遍历方向

挖掘 / 填充 / 铺盖 / 排流体 / 破基岩可以指定候选方块**优先从哪个方向开始**，
选项按轴标注：

| 选项 | 含义 |
| --- | --- |
| 最近优先（默认） | 围绕玩家由近及远 |
| `Y ↑` | 下 → 上 |
| `Y ↓` | 上 → 下 |
| `Z →` | 北 → 南 |
| `Z ←` | 南 → 北 |
| `X →` | 西 → 东 |
| `X ←` | 东 → 西 |

### 工具切换

默认交给 Tweakeroo：开着它的自动切工具就由它处理，没开就保持当前手持物品。
需要打印机自己选时，打开 **破坏引擎 → 自动切换工具**（默认关闭）；打开后如果 Tweakeroo
没有换槽，打印机会用自己的选择器补一次。破基岩流程始终会自行选镐。

完整参数说明见 [docs/服务器推荐配置.md](docs/服务器推荐配置.md)。

---

## 支持的 Minecraft 版本

1.18.2 · 1.19.4 · 1.20.1 · 1.20.2 · 1.20.4 · 1.20.6 · 1.21.1 · 1.21.3 · 1.21.4 · 1.21.5 ·
1.21.6 · 1.21.9 · 1.21.11 · 26.1 · 26.2 · 26.3

一次构建全部产出，也可以只装 `litematic-printer-Ling-all.jar`（内含全部版本的子 JAR）。

## 前置模组

必需：

- [Fabric Loader](https://fabricmc.net/)
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [MaLiLib](https://modrinth.com/mod/malilib)
- [Litematica](https://modrinth.com/mod/litematica)

可选联动：

- [Tweakeroo](https://modrinth.com/mod/tweakeroo)（自动切换工具、耐久保护）
- [Quick Shulker](https://github.com/MoRanpcy/quickshulker)（快捷潜影盒，默认关闭）
- [Take It Out](https://modrinth.com/mod/takeitout)（仅受支持的 1.21.x / 26.x）
- [Chest Tracker](https://github.com/ponuing/ChestTracker)（远程取物，默认关闭）
- [Neko-Carpet-Addition](https://github.com/YoshinoShizuka/Neko-Carpet-Addition)（服务端虚空仓库，1.21.11+）

所有前置都要选与当前 Minecraft 版本匹配的构建。

---

## 下载与发布通道

唯一官方发布源是 **[GitHub Releases](https://github.com/ling23323/Litematica-Printer-Ling/releases)**：

| 文件 | 用途 |
| --- | --- |
| `litematic-printer-Ling-<MC版本>.jar` | 单版本，装哪个 Minecraft 版本就下哪个 |
| `litematic-printer-Ling-all.jar` | 全版本合一，内含 1.18.2 ~ 26.3 共 16 个子 JAR |

- Actions 自动构建的产物标记为 **Alpha**，手动发布的 Release 标记为 **Beta**。
- 目前没有正式版通道，也没有上传到 Modrinth。
- 每个 Release 页面同时提供该版本对应的源码压缩包。

> 从其它渠道下载的包请自行核对；本仓库不为第三方转发的文件负责。

---

## 安装

1. 安装 Fabric Loader 与上面的必需前置。
2. 把对应 Minecraft 版本的 jar 放进 `mods/`：
   - 单版本：`litematic-printer-Ling-<版本>.jar`
   - 全版本：`litematic-printer-Ling-all.jar`
3. 进游戏按 `Z,Y` 打开设置，默认停在「核心」页签。

---

## 从源码构建

```bash
git clone https://github.com/ling23323/Litematica-Printer-Ling.git
cd Litematica-Printer-Ling
```

**需要 JDK 25。** JDK 26/27 会让项目的 Lombok 1.18.42 直接崩溃（`com.sun.tools.javac.tree.EndPosTree` 已被移除）。

```bash
# 全部版本，产物在 build/libs/<mod_version>/<mc_version>/
./gradlew buildAndCollect

# 只要 26.3
./gradlew :26.3:buildAndCollect

# 全版本合一的 wrapper
./gradlew :fabricWrapper:build
```

Windows 下可以直接双击 `build-all-versions.bat`：它会依次在 `%JAVA_HOME%`、`D:\Java\jdk-25.0.3`、`C:\Program Files\...` 下找 JDK 25，找不到会给出提示。

构建会跑完整检查：单元测试、架构边界校验 `verifyArchitecture`、JaCoCo 覆盖率门禁。

---

## 目录结构

| 路径 | 说明 |
| --- | --- |
| `src/main/java/ling/printer/` | 全部模组源码（`handler` 功能模块、`core` 扫描与运行时、`placement` 放置基础设施、`integration` 外部联动） |
| `src/main/resources/` | `fabric.mod.json`、mixin 配置、语言文件 |
| `versions/` | 预处理器生成的各 Minecraft 版本工程（由 `src` 自动生成，不要手改） |
| `buildSrc/` | Gradle 约定插件与版本号规则 |
| `fabricWrapper/` | 把全部版本的 jar 打包成一个"全版本"模组的工程 |
| `docs/` | 使用说明、发布清单与上传指引：[服务器推荐配置](docs/服务器推荐配置.md)、[发布前检查清单](docs/发布前检查清单.md)、[上传到 GitHub](docs/上传到GitHub.md) |
| `mod/` | 手动上传用的成品 jar（该目录的 `*.jar` 不被忽略，见 [mod/README.md](mod/README.md)） |
| `.github/` | Actions 工作流、Issue 模板、PR 模板、发布脚本 |
| `NOTICE.md` | 来源、修改说明、AGPL 合规声明 |

根目录只保留构建入口与通用文档；`build/`、`.gradle/`、`libs/`、`backup/`
都是本地产物或本地留存，不会进入仓库。

---

## 致谢、来源与许可

本项目是 **修改版本**，基于多位作者的成果继续维护，版权归原作者所有：

- [aleksilassila/litematica-printer](https://github.com/aleksilassila/litematica-printer)（原始作者）
- [Yur1Ca/litematica-printer](https://github.com/Yur1Ca/litematica-printer)（Hana 分支）
- [zhaixianyu](https://github.com/zhaixianyu)、[BlinkWhite](https://github.com/BiliXWhite)、[bunnyi116](https://github.com/bunnyi116)

许可证为 **GNU Affero General Public License v3.0（AGPL-3.0）**，全文见 [LICENSE.md](LICENSE.md)。
修改说明、上游署名与源码获取方式见 [NOTICE.md](NOTICE.md)（满足 AGPL 第 5 条的告知义务）。

这意味着：

- 你可以自由使用、修改、再分发本模组。
- 但**分发本模组或它的修改版时**，必须：
  1. 以 AGPL-3.0 授权整个作品，不附加额外限制；
  2. 提供完整的对应源码；
  3. 保留原作者署名与许可证声明，并注明你做过修改。
- 每个构建出的 jar 都已内嵌 `META-INF/LICENSE.md`、`META-INF/NOTICE.md` 与
  `META-INF/THIRD-PARTY.md`（第三方组件许可见 [THIRD-PARTY.md](THIRD-PARTY.md)）。

本作品按「现状」提供，不附带任何担保。本仓库为社区分支，不代表上游作者，也未获其官方背书。

