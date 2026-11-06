# 参与开发

## 环境要求

| 项目 | 版本 |
| --- | --- |
| JDK | **25**（必须；JDK 26/27 会让 Lombok 1.18.42 崩溃） |
| Gradle | 用仓库自带的 `gradlew`，不要装全局 Gradle |
| Minecraft | 见 `versions/*/gradle.properties`，主版本记录在 `versions/mainProject` |

## 常用命令

```bash
./gradlew :26.3:build            # 编译 + 单元测试 + 架构校验 + 覆盖率门禁
./gradlew :26.3:compileJava      # 只编译
./gradlew :26.3:test             # 只跑测试
./gradlew buildAndCollect        # 全部版本打包并收集到 build/libs/
./gradlew :fabricWrapper:build   # 全版本合一 wrapper
```

Windows 下可执行 `build-all-versions.bat`：优先用 `%JAVA_HOME%`，否则在常见安装路径里找 JDK 25。

## 源码结构

```
src/main/java/ling/printer/
├── core/            架构底层：扫描游标、运行时作用域、动作协调、诊断计时
├── handler/         功能模块：print / mine / fill / fluid / cover / bedrock
│   └── scan/        扫描引擎、会话、缓存、候选游标
├── placement/       放置基础设施：选区盒、朝向、动作队列、速率控制
│   └── action/      动作端口与仲裁（ActionPort / ActionBroker）
├── feature/         纯计算的功能规划器（不接触 Minecraft 交互）
├── integration/     外部联动：litematica / tweakeroo / quickshulker / chesttracker / voidwarehouse
├── interaction/     挖掘交互控制器与工具准备
├── guide/           各方块家族的放置规则
├── mixin/           Mixin 注入，按目标模组分子包
├── render/          HUD 与高亮渲染
├── runtime/         运行时装配与事件
├── config/          配置项、构建器、界面连接
└── utils/           物品、交互、消息等通用工具
```

`versions/` 下的内容由预处理器根据 `src` 自动生成，**不要手动修改**。
写跨版本代码请使用源码里的预处理指令：

```java
//#if MC >= 260100
    新版本 API
//#else
//$$ 旧版本 API
//#endif
```

## 提交前自查

1. `./gradlew :26.3:build` 必须全绿——里面包含：
   - 单元测试（版本专属用例会用 `//#if` 限定）
   - `verifyArchitecture`：检查 `core/` 与 `feature/` 的依赖边界、mixin 行数上限、禁止的调用等
   - JaCoCo 覆盖率：`core` 包行覆盖 ≥ 85%、分支覆盖 ≥ 75%
2. 改了共享源码就要跑一次全量 `buildAndCollect`，确认低版本也能编译。
3. 新增配置项必须同时补 `assets/litematic-printer-ling/lang/` 下的
   `en_us.json`（必填）和 `zh_cn.json` / `zh_tw.json`，键名格式为
   `litematic-printer-ling.config.<key>` 与 `...<key>.desc`。
4. 新增用户可见行为请更新 `CHANGELOG.md`。

## 提交信息

推荐 `类型: 简述` 的形式，类型用 `feat` / `fix` / `perf` / `refactor` / `docs` / `build` / `test`。
一次提交只做一件事，方便回溯。

## 许可证与文件头

本项目以 **AGPL-3.0** 发布，来源与修改说明见 [NOTICE.md](NOTICE.md)。

- **修改上游文件时，不要删除原作者的文件头或署名**；如需添加，加在原有声明之后。
- **新建的源文件**建议带上下面的文件头（复制后改年份/作者）：

```java
/*
 * Litematic Printer Ling — a modified version of Litematica Printer.
 * Copyright (C) 2026 <你的名字>
 * Copyright (C) the Litematica Printer authors (see NOTICE.md)
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details: <https://www.gnu.org/licenses/>.
 */
```

提交代码即表示同意以 AGPL-3.0 发布。分发构建产物时，`META-INF/LICENSE.md` 与
`META-INF/NOTICE.md` 会由构建脚本自动打包进 jar（见 `buildSrc/.../ModPlugin.kt`）。

