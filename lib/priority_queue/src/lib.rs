use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::{jdouble, jlong};

struct PriorityQueue {
    array: Vec<f64>
}

impl PriorityQueue {
    // implemented as a binary heap. I'm not that familiar with JNI so I will not use generic types.
    // This priority queue will be for f64 (doubles in java). This heap will also be a min heap.
    // Also no use in having public functions as they will all be called in this file.
    fn new() -> PriorityQueue {
        PriorityQueue { array: Vec::new() }
    }

    fn add(&mut self, val: f64) {
        self.array.push(val);

        // make sure that this is still a heap
        let mut i = self.array.len() - 1;
        while i != 0 && self.array[i] < self.array[self.parent(i)] {
            self.swap(i, self.parent(i));
            i = self.parent(i);
        }
    }

    fn poll(&mut self) -> f64 {
        self.array[0]
    }

    fn pop(&mut self) -> f64 {
        let ret = self.array[0];

        self.array[0] = self.array[self.array.len() - 1];
        self.array.remove(self.array.len() - 1);
        self.heapify(0);

        ret
    }

    fn heapify(&mut self, i: usize) {
        let l = self.left(i);
        let r = self.right(i);

        let mut smallest = i;
        if l < self.array.len() && self.array[l] < self.array[i] {
            smallest = l;
        } else if r < self.array.len() && self.array[r] < self.array[i] {
            smallest = r;
        }

        if smallest != i {
            self.swap(i, smallest);
            self.heapify(smallest);
        }
    }

    fn parent(&self, i: usize) -> usize {
        (i - 1) / 2
    }

    fn left(&self, i: usize) -> usize {
        2 * i + 1
    }

    fn right(&self, i: usize) -> usize {
        2 * i + 2
    }

    fn swap(&mut self, i: usize, j: usize) {
        let tmp = self.array[i];
        self.array[i] = self.array[j];
        self.array[j] = tmp;
    }
}

#[no_mangle]
pub extern "system" fn Java_util_NativeQueue_newQueue(_env: JNIEnv, _class: JClass) -> jlong {
    let priority_queue = PriorityQueue::new();

    Box::into_raw(Box::new(priority_queue)) as jlong
}

#[no_mangle]
pub unsafe extern "system" fn Java_util_NativeQueue_add(_env: JNIEnv, _class: JClass, pointer: jlong, input: jdouble) {
    let priority_queue = &mut *(pointer as *mut PriorityQueue);

    priority_queue.add(input);
}

#[no_mangle]
pub unsafe extern "system" fn Java_util_NativeQueue_pop(_env: JNIEnv, _class: JClass, pointer: jlong) -> jdouble {
    let priority_queue = &mut *(pointer as *mut PriorityQueue);

    priority_queue.pop()
}

#[no_mangle]
pub unsafe extern "system" fn Java_util_NativeQueue_poll(_env: JNIEnv, _class: JClass, pointer: jlong) -> jdouble {
    let priority_queue = &mut *(pointer as *mut PriorityQueue);

    priority_queue.poll()
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        assert_eq!(2 + 2, 4);
    }
}
