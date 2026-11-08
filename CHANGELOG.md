# 更新日志

本文件记录 Litematic Printer Ling 的变更。版本号沿用上游的 `0.7.2.x` 系列，
并以 `-Ling` 后缀标记本分支。

---

## 0.7.2.2-Ling

正式发布版。相比 `0.7.2.1-Ling`，补齐了缺失的功能开关、重做模式体系，并统一了界面文案。

### 修复

- 自定义模式下「破基岩」缺失：破基岩开关原本在「快捷键」页签，与另外五个功能开关不在同一页，
  自定义模式里勾不到。现已移到「核心」页签，六个功能开关并排显示。
- 五个功能开关在「核心」与「快捷键」两个页签各出现一次（重复条目），已移除快捷键页签里的重复项。
- 「覆盖打印」（`printReplace` + `printReplaceableList`）之前只参与扫描缓存哈希、不影响判定，
  界面上开着也不起作用。现在名单内的方块会被当作「可以直接放上去」，不跳过也不先破坏。

### 新增

- **自定义工作模式**：与单模式、多模式并列的第三种模式。在核心页签勾选要用的功能后，
  「切换模式」热键只在这些功能之间循环；选到未勾选的功能或一个都没勾时，action bar 会提示原因。
- **自动切换工具**（破坏引擎，默认关闭）：默认仍交给 Tweakeroo；
  打开后若 Tweakeroo 没有换槽，打印机会用自己的选择器补一次。

### 调整

- 「服务器安全模式」简化为「安全模式」，配置标题、描述与切换提示同步更新。
- 遍历方向改为轴 + 箭头标注：`Y ↑`、`Y ↓`、`Z →`、`Z ←`、`X →`、`X ←`。
- 打印 / 挖掘 / 填充 / 铺盖 / 排流体 / 破基岩 六个页签的颜色由金色 `§6` 改为青色 `§b`。
- 本地构建的版本号不再追加 `-local` 后缀。
- Issue 模板中的讨论链接改为 Issues（仓库未启用 Discussions，原链接会 404）。
- 清理语言文件中已无代码引用的废弃键（`breakListMode.*`、`printerSelectionType.desc`、
  `printMode.replace*`）。

---

## 0.7.2.1-Ling（内部整理版，未单独发布）

基于上游 Hana 分支的 26.3 源码整理而来的首个可运行版本。

### 修复

- 修复项目无法编译的问题：上一轮改造留下的 `WorldUtilsMixin` 残片、
  `scheduler/` 任务池脚手架、`PrinterConfig` / `CapabilityManager` 等文件
  引用了不存在的方法与类，已全部移除（内容保留在仓库外的备份中）。
- 修复 `PlayerUtils` 把 `Minecraft` 实例缓存在静态字段里导致的空指针：
  该类在客户端装载前被初始化时会永久持有 `null`，现在会回落到当前实例。
- 新增单模式下的「切换模式」热键（`switchPrinterMode`），默认不绑定按键，避免与其它模组撞键。

### 变更

- 模组重命名为 **Litematic Printer Ling**，id 为 `litematic-printer-ling`，
  配置文件变为 `config/litematic-printer-ling.json`。
- jar 命名统一为 `litematic-printer-Ling-<MC版本>.jar`，全版本包为 `litematic-printer-Ling-all.jar`。
- 默认参数调整为公共服安全档：
  放置间隔 2 tick、每刻放置 3 个、每刻挖掘 8 个、放置冷却 6 tick、挖掘冷却 6 tick。
- 配置页签顺序改为：核心 → 放置 → 破坏 → 打印 → 挖掘 → 填充 → 铺盖 → 排流体 → 破基岩 → 特殊 → 热键。
- **移除内置的自动切换工具**（`breakAutoTool`），工具切换完全交给 Tweakeroo；
  破基岩流程仍保留自带的选镐逻辑。

### 优化

- 包结构整理：历史遗留的 `ling.printer.printer`（上游 `me.aleksilassila.litematica.printer.printer`
  改名后的产物）重命名为 `ling.printer.placement`，消除嵌套同名包。
- 扫描入口 5 个重载收敛为 `ScanQuery` 参数对象，`ScanCache` / `ScanEngine`
  各只保留一个实现方法。
- 统一 `ActionSource` / `SendResult` 枚举，删除 `ActionBroker` 里的字符串映射。
- `ConfigUtils` 的 6 个模式判断合并为一个 `isFeatureActive`，`ModuleEnablePolicy` 复用同一逻辑。
- 删除空实现的 `MineDebugLog` 及其 9 处调用点——它每 tick 都在拼字符串却什么也不输出。
- 抽取破基岩站位数、清理队列、挖掘收尾三处重复代码。
- 项目内 8 行滑动窗口重复块从 8 组降到 2 组。

### 构建

- 全量构建需要 JDK 25；JDK 26/27 会因 Lombok 不兼容而失败。
- 新增 `build-all-versions.bat` 便于本地一键构建。
