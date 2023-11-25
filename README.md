# A tool to implement APP sensitive method call detection through Xposed

[README 中文]

## Install and use

1. Install the Xposed framework or other sandbox tools that use the Xposed framework without root, such as [Tai Chi], [Fuxi], etc.
2. Install this tool and open the APP once to ensure that the data is received normally (currently using static broadcast to receive data, when testing on [Fuxi], the data cannot be received normally without actively starting it once)
3. Open the APP that needs to be tested. After the test is completed, return to WatchPrivacyDog and view the call stack.



## Current functions

- [ ] Support manual opening and closing, to be tested
- [x] Clear recorded information
- [x] View recorded information
- [x] Monitor multiple APPs simultaneously
- [x] Copy a single call stack
- [x] LogCat output detailed stack information

## Follow-up planning

- [ ] Single APP/Multiple APP information sharing and export
- [ ] Floating window to view the current APP call stack

[Tai Chi]: https://taichi.cool
[Fuxi]: https://github.com/Katana-Official/SPatch-Update
[README 中文]: https://github.com/oOJohn6Oo/WatchPrivacyDog/edit/main/README_ZH_hans.md
