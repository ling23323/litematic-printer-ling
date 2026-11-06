# 第三方组件与许可

本项目的全部源码以 **AGPL-3.0** 授权（见 [LICENSE.md](LICENSE.md)）。
构建产物中额外打包了下面这个第三方库，其许可与版权归原作者所有。

---

## pinyin4j 2.5.1

| 项目 | 内容 |
| --- | --- |
| Maven 坐标 | `com.belerweb:pinyin4j:2.5.1` |
| 许可 | **BSD**（其 POM 中声明为 `http://opensource.org/licenses/bsd-license.html`） |
| 上游项目 | <https://pinyin4j.sourceforge.net/> · <https://github.com/belerweb/pinyin4j> |
| 用途 | 中文方块名的拼音匹配（配置里的黑名单/白名单可以打拼音） |
| 打包方式 | 通过 Gradle `include()` 嵌入到 `META-INF/jars/pinyin4j-2.5.1.jar` |

该库的 jar 中没有附带许可证文件，因此按其许可要求，在此保留许可文本：

```
Copyright (c) the pinyin4j authors. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```

> 说明：pinyin4j 内部还包含了 `com.hp.hpl.sparta`（Hewlett-Packard 的 XML 解析器），
> 其源码文件中保留有 `Copyright (C) 2002 Hewlett-Packard Company` 声明。
> 这些声明随 pinyin4j 一起分发，本项目未做修改。

---

## 仅在构建/运行环境使用、不随产物分发的依赖

以下依赖不会被打进 jar，仅供编译或作为运行时前置，由用户自行安装：

| 依赖 | 许可 | 说明 |
| --- | --- | --- |
| Fabric Loader / Fabric API | Apache-2.0 | 模组运行环境 |
| MaLiLib / Litematica / Tweakeroo | LGPL-3.0（上游项目自行声明） | 必需或可选前置 |
| `me.fallenbreath:conditional-mixin-fabric` | LGPL-3.0 | 编译期库，未打包 |
| Quick Shulker / Take It Out / Chest Tracker | 各自项目声明 | 可选联动，未打包 |

如对上述组件的许可有疑问，请以各自项目的声明为准。
