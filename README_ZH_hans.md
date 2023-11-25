# 通过 Xposed 实现 APP 敏感方法调用检测的工具

[README EN]

## 安装使用

1. 安装 Xposed 框架或者其他免 Root 使用 Xposed 框架的沙盒工具，如[太极]、[伏羲]等
2. 安装本工具,并打开一次 APP 确保数据接收正常（目前使用静态广播接收数据，在[伏羲]上测试不主动启动一次无法正常接收数据）
3. 打开需要测试的 APP，测试完毕返回 WatchPrivacyDog，查看调用堆栈



## 目前功能

- [ ] 支持手动开启关闭 待测试
- [x] 清空已记录信息
- [x] 查看已记录信息
- [x] 多 APP 同时监控
- [x] 复制单个调用堆栈
- [x] LogCat 输出详细堆栈信息

## 后续规划

- [ ] 单 APP /多 APP 信息分享导出
- [ ] 悬浮窗查看当前 APP 调用堆栈
- [ ] Material You Dynamic Color 支持

[太极]: https://taichi.cool
[伏羲]: https://github.com/Katana-Official/SPatch-Update
[README EN]: https://github.com/oOJohn6Oo/WatchPrivacyDog/edit/main/README.md
