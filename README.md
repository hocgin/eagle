## EAGLE
> STATUS: Development
- [Eagle UI](https://github.com/hocgin/eagle-ui)
- [Eagle Mall](https://github.com/hocgin/eagle-mall)
- [Eagle](https://github.com/hocgin/eagle)

## Deploy
### Eagle
1. 检查环境变量, 参考`.env.example`
2. 检查HOST, 参考`.host.example`
3. 修改`yml`配置参数
4. 设置参数`spring.profiles.active=dev`并启动`AppApplication`
5. 执行 `sh scripts/docker-compose.sh`

**内网穿透**请参考 `scripts/proxy.sh` 内网穿透(`仅支持MacOS`)

### Eagle UI
1. 基础环境，Node
2. `npm install`
3. `npm start`

### Eagle Mall
1. 基础环境，Node
2. `npm install`
3. `npm serve`

## UI
### 移动商城
<img src="http://cdn.hocgin.top/mobile.gif" width="404" alt="UI"/>

### 管理平台
<img src="http://cdn.hocgin.top/pc1.gif" width="808" alt="UI"/>
<img src="http://cdn.hocgin.top/3A30AEEC-9D0B-40C1-B271-BCA9C37F7113.png" width="808" alt="UI"/>
