# 更新日志

本文件记录 Litematic Printer Ling 的变更。版本号沿用上游的 `0.7.2.1` 系列，
并以 `-Ling` 后缀标记本分支。

---

## 0.7.2.1-Ling

首个可发布版本，基于上游 Hana 分支的 26.3 源码整理而来。

### 修复

- 修复项目无法编译的问题：上一轮改造留下的 `WorldUtilsMixin` 残片、
  `scheduler/` 任务池脚手架、`PrinterConfig` / `CapabilityManager` 等文件
  引用了不存在的方法与类，已全部移除（内容保留在仓库外的备份中）。
- 修复 `PlayerUtils` 把 `Minecraft` 实例缓存在静态字段里导致的空指针：
  该类在客户端装载前被初始化时会永久持有 `null`，现在会回落到当前实例。
- 新增单模式下的「切换模式」热键（`switchPrinterMode`），默认不绑定按键，避免与其它模组撞键。

### 新增

- **服务器安全模式**：核心页签第一项，一键在保守档与性能档之间切换；
  安全档的三项数值可在配置里自定义。
- 开启安全模式时手动修改任一速率参数会自动退出安全模式，并保留修改后的值。
- **自定义工作模式**：单模式下新增「自定义」选项，按各功能的勾选状态运行，
  可以只开打印 + 挖掘 + 填充这样的组合。
- **遍历方向**：挖掘 / 填充 / 铺盖 / 排流体 / 破基岩支持
  最近优先 / 从下往上 / 从上往下 / 从北往南 / 从南往北 / 从西往东 / 从东往西。
- 缺材料 HUD 默认开启。
- 逐方块失败提示增加节流，并且只在真正显示时才构建文本。

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
