# NOTICE — 来源、修改说明与许可证

本文件用于满足 **GNU Affero General Public License v3.0**（下称 AGPL-3.0）
第 5 条对修改版本提出的告知义务：声明本作品是修改版、说明修改内容与日期、
保留原作者署名，并指明获取完整源码的位置。

许可证全文见 [LICENSE.md](LICENSE.md)。

---

## 1. 本作品是什么

**Litematic Printer Ling** 是 [Litematica Printer](https://github.com/Yur1Ca/litematica-printer)
的**修改版本**（modified version / work based on the Program），
用于 Minecraft 的客户端 Fabric 模组。

它保留上游全部功能，并调整了公共服默认参数、操作入口与界面组织。
修改条目逐条记录在 [CHANGELOG.md](CHANGELOG.md) 中。

## 2. 上游作品与版权

本作品的绝大部分源码来自以下项目，版权归原作者所有：

| 项目 | 说明 |
| --- | --- |
| [aleksilassila/litematica-printer](https://github.com/aleksilassila/litematica-printer) | 原始作者（aleksilassila） |
| [Yur1Ca/litematica-printer](https://github.com/Yur1Ca/litematica-printer) | Hana 分支（Yur1Ca、zhaixianyu、BlinkWhite、bunnyi116） |

原始版权声明、作者署名与许可证声明均原样保留，未做删除或弱化：

- `LICENSE.md` 为 AGPL-3.0 官方全文，未做任何修改
- `fabric.mod.json` 的 `authors` 字段保留全部上游作者
- `README.md` / `README.en.md` 的「致谢」小节保留来源链接

## 3. 修改说明（AGPL-3.0 §5(a)）

- **修改者**：Ling（本仓库维护者）
- **修改起始日期**：2026-11-06
- **修改范围摘要**：
  - 重新命名项目与模组 id（`litematic-printer-ling`）、Java 包名（`ling.printer`）
  - 新增服务器安全模式与其自定义安全档、自定义工作模式、遍历方向配置
  - 移除内置的自动切换工具，改为完全交由 Tweakeroo
  - 重排配置页签、调整默认速率参数、增加提示节流与缺材料 HUD 默认开启
  - 收敛扫描入口参数、合并重复枚举与重复代码块

完整差异以本仓库的提交历史为准。

## 4. 许可证（AGPL-3.0 §5(b)）

本作品整体以 **AGPL-3.0** 授权，不附加任何额外限制。
任何取得本作品副本的人，都可以在 AGPL-3.0 的条款下使用、修改与再分发。

许可证全文见 [LICENSE.md](LICENSE.md)，也可从
<https://www.gnu.org/licenses/agpl-3.0.html> 获取。

## 5. 完整对应源码（AGPL-3.0 §6 / §13）

本作品的完整对应源码（Corresponding Source）随每一份二进制产物一同提供：

- 仓库地址：`https://github.com/ling23323/Litematica-Printer-Ling`
- 每个 Release 页面同时附有与该版本对应的源码压缩包（GitHub 自动生成）
- 分发构建出的 jar 时，`META-INF/LICENSE.md`、`META-INF/NOTICE.md` 与
  `META-INF/THIRD-PARTY.md` 会一并打包在内

## 5.1 第三方组件

产物里还嵌入了 `com.belerweb:pinyin4j:2.5.1`（BSD 许可，用于中文方块名的拼音匹配）。
它的版权与许可文本见 [THIRD-PARTY.md](THIRD-PARTY.md)，该文件同样会打入 jar。

## 6. 无担保

本作品按「现状」提供，不附带任何明示或默示的担保，包括但不限于对适销性与特定用途适用性的担保。
使用本作品的全部风险由使用者承担。详见 AGPL-3.0 第 15、16 条。

## 7. 与上游无隶属关系

本仓库是社区分支，**不代表**上游作者，也未获得上游作者的官方背书。
请勿把本仓库的问题反馈到上游项目。
