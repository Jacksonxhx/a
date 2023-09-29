const AC_GAME_OBJECT = [];

export class AcGameObject {
    constructor() {
        AC_GAME_OBJECT.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    start() { // 只执行一次 
        
    }

    uodate() { // 每一帧执行一次，除了第一帧之外

    }

    on_destroy() { // 删除之前执行

    }

    destroy() {
        this.on_destroy();

        for (let i in AC_GAME_OBJECT) {
            const obj = AC_GAME_OBJECT[i];
            if (obj == this) {
                AC_GAME_OBJECT.splice(i);
                break;
            }
        }
    }
}

let last_timestamp; //上一次执行的时刻
const step = timestamp => { // 通过递归实现每秒钟执行60次
    for (let obj of AC_GAME_OBJECT) { // 遍历所有的object
        if (!obj.has_called_start) { // 判断是否执行过start
            obj.has_called_start = true;
            obj.start();
        } else { //若执行过start，执行update
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    } 

    last_timestamp = timestamp; //更新上次执行的时间
    requestAnimationFrame(step) //recursion
}

requestAnimationFrame(step)