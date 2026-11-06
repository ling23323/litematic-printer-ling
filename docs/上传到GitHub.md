# 用 GitHub Desktop 发布

仓库地址：<https://github.com/ling23323/Litematica-Printer-Ling>

项目目录：`D:\Code\litematic-printer-ling`

---

## 0. 上传前先知道

| | 内容 |
| --- | --- |
| 会被提交 | **676 个文件 / 约 3.4 MB** |
| 最大单文件 | 69 KB（`ru_ru.json`） |
| 自动忽略 | `build/`、`versions/*/build/`、`.gradle/`、`libs/`、`backup/`、`logs/`、`run/`、`*.jar`（除 `gradle-wrapper.jar`） |

忽略规则写在 `.gitignore` 里，**GitHub Desktop 会自动遵守**。但提交前仍要扫一眼 Changes，确认没有出现这些目录。

---

## 1. 安装并登录

1. 下载 <https://desktop.github.com/> 并安装。
2. 打开后 **File → Options → Accounts** 登录 `ling23323`。

## 2. 让项目变成 Git 仓库（关键一步）

GitHub Desktop 的「Add local repository」只能添加**已经是 Git 仓库**的文件夹。
你的项目目录目前没有 `.git`，所以先二选一：

### 方式 A：用 Desktop 自带的 Git 初始化（推荐，只需一次）

GitHub Desktop 自带 `git.exe`，不用另外安装。**装好 Desktop 之后**，
在项目目录打开 PowerShell，粘贴执行：

```powershell
$git = (Get-ChildItem "$env:LOCALAPPDATA\GitHubDesktop\app-*\resources\app\git\cmd\git.exe" |
        Select-Object -First 1).FullName
cd D:\Code\litematic-printer-ling
& $git init
& $git add .
& $git commit -m "feat: initial public release of Litematic Printer Ling"
& $git branch -M main
& $git remote add origin https://github.com/ling23323/Litematica-Printer-Ling.git
```

执行完 `git add .` 后可以核对文件数：

```powershell
& $git ls-files | Measure-Object -Line    # 期望约 676
```

### 方式 B：先在 Desktop 里试「就地创建」

1. **File → Add local repository…** → 选 `D:\Code\litematic-printer-ling`
2. 若提示 *"This directory does not appear to be a Git repository"*，点 **create a repository**
3. 创建对话框里把 **Name** 设为 `Litematica-Printer-Ling`、**Local path** 设为 `D:\Code`，
   三个复选框（README / Git ignore / License）全部**不勾**
4. 点 **Create repository**
   - 成功 → 进入第 3 步
   - 若报「目录非空 / already exists」→ 退回方式 A

### 方式 C：装 Git for Windows

<https://git-scm.com/download/win>，然后在项目目录执行：

```bash
git init
git add .
git commit -m "feat: initial public release of Litematic Printer Ling"
git branch -M main
git remote add origin https://github.com/ling23323/Litematica-Printer-Ling.git
```

---

## 3. 回到 Desktop：确认仓库与分支

1. **File → Add local repository…** → 选 `D:\Code\litematic-printer-ling` → **Add Repository**
2. 看顶部中间的分支按钮：
   - `main` → 继续
   - `master` → 点它 → **Rename branch…** → 改成 `main`（CI 只监听 `main`）

## 4. 推送前核对 Changes

点 **Changes** 标签：

- 应该显示 **约 676 changed files**（如果你已在命令行 commit 过，这里会是空的，正常）
- 列表里**不应**出现 `build/`、`backup/`、`libs/`、`.gradle/`、`versions/*/build/`
- 出现的话：检查根目录 `.gitignore` 是否存在，别提交

若还有未提交的改动：Summary 填 `feat: initial public release of Litematic Printer Ling`，点 **Commit to main**。

## 5. 设置远端并推送

仓库在 GitHub 上已经存在，**不要点 Publish repository**（那会新建一个仓库）。

1. 菜单 **Repository → Repository settings…** → **Remote** 标签
2. **Primary remote repository (origin)** 填：
   ```
   https://github.com/ling23323/Litematica-Printer-Ling.git
   ```
3. **Save**
4. 点顶部 **Push origin**

如果远端已有 README 等文件，会先出现 **Pull origin**：
先点 Pull（提示分叉就选 **Pull with rebase**），再 Push。

## 6. 上传后核对

| 检查 | 期望 |
| --- | --- |
| 仓库首页 | 约 **676 个文件** |
| `versions/` | 只有 16 个 `gradle.properties` + `mainProject` + 4 个 `mapping-*.txt` |
| 根目录 | 没有 `build`、`libs`、`backup`、`.gradle` |
| **Actions** | `build` 工作流自动运行并变绿（约 10 分钟） |

**About 建议**

- Description：`Litematica 自动打印模组，默认参数面向公共服`
- Topics：`minecraft` `fabric` `litematica` `printer` `schematic` `26.3`

## 7. 发布 Release（上传 jar）

Desktop 不能建 Release，用网页：

1. 仓库页 **Releases → Draft a new release**
2. Tag 填 `v0.7.2.1-Ling`，选 *Create new tag on publish*
3. 标题 `0.7.2.1-Ling`，说明可复制 `CHANGELOG.md` 对应段落
4. 拖入 jar：
   - `fabricWrapper/build/libs/litematic-printer-Ling-all.jar`（15.7 MB，含全部 16 个版本）
   - 需要单版本再补 `build/libs/0.7.2.1-Ling/<版本>/litematic-printer-Ling-<版本>.jar`
5. 勾 **Set as a pre-release** → **Publish release**

> 也可以走仓库自带工作流：**Actions → release-version-set → Run workflow**，
> tag 填 `v0.7.2.1-Ling`，版本集选 `all`，自动构建并建 Release。

## 8. 以后怎么更新

改完代码 → 打开 Desktop → 填 Summary → **Commit to main** → **Push origin**。
不要再用网页上传文件，容易和本地版本冲突。

---

## 常见提示对照

| 提示 | 处理 |
| --- | --- |
| *does not appear to be a Git repository* | 先做第 2 步（方式 A/B/C） |
| *directory already exists / not empty* | 用方式 A 或 C |
| *repository already exists* | 你点了 Publish；改用第 5 步手动设置 remote |
| *diverged / Pull origin* | 先 Pull with rebase，再 Push |
| *files are too large* | 误把 `build/` 或 `.gradle/` 加入，取消勾选 |
| Changes 数量远大于 676 | `.gitignore` 未生效，检查根目录 |
| Actions 报 `JDK 25 not found` | 检查 `.github/workflows/build.yml` |
