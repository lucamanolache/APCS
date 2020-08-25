Primitives
==========

There are 8 primitives in Java.
They are: *int*, *byte*, *short*, *long*, *float*, *double*, *boolean*, and *char*.
Strings, while not being a primitive data type is still similar in some ways.

### Bits ###
Bits are stored in a computer as electrical signals.
Most of the time a high voltage means 1 or true and a low voltage means 0 or false.

### Integers ###
Integers are one of the most basic and most commonly used types of primitive objects.
They are defined by using the ``int`` keyword. An integer is stored in 32 bits (4B) and is signed.
This means it can store values from -2,147,483,648 (-2<sup>31</sup>) to 2,147,483,647 (2<sup>31</sup>-1).
Like the name suggests, integers can not store decimals.

### Bytes ###
Bytes are similar to ints however the range of values bytes can store is significantly lower.
Bytes are stored in 8 bits (1B) of memory and are signed.
This limits what they can store from -128 (-2<sup>7</sup>) to 127 (2<sup>7</sup> – 1).
You can declare a byte by typing ``byte``.

### Shorts ###
Shorts are a compromise between ints and bytes.
They are a 16 bit (2B) signed data type.
This means they can store values from -32,768(-2<sup>15</sup>) to 32,767(2<sup>15</sup> – 1).
Shorts are declared by typing ``short``.

### Longs ###
Longs are similar to all of the previous data types.
Longs are a 64 bit (8B) signed data type.
This is significantly larger than an int or a short.
This totals out to them being able to store values between -9,223,372,036,854,775,808 (-2<sup>63</sup>) to 9,223,372,036,854,775,807 (2<sup>63</sup> – 1).
They are defined by typing ``long``.

### Floats ###
Floats are the one of the 2 primitives able to store decimals.
Floats are a 32 bit (4B) signed data type.
This means the smallest decimal they can store is around  10<sup>-45</sup>, and the largest value is around 10<sup>38</sup>.
They are defined by typing ``float``.

### Doubles ###
Doubles are the one of the 2 primitives able to store decimals.
Doubles are a 64 bit (8B) signed data type.
This means the smallest decimal they can store is around  10<sup>-324</sup>, and the largest value is around 10<sup>308</sup>.
They are defined by typing ``double``.

### Characters ###
Characters is the only primitive data type that is used to store letters.
Characters are a 16 bit (2B) unsigned data type.
They represent a single unicode character.
They are defined by typing ``char``.

### Booleans ###
Booleans are the most basic primitive and stores its value in 1 bit.
Despite only taking 1 bit, Java still pads it to 1 byte.
This means they can only store a 0 or a 1.
They are mainly used for storing true/false.
They are defined by typing ``double``.

### Strings ###
Although strings are technically not a primitive data type, they can be used similar one and are very simple.
Strings store a series of characters which means a word or series of words.
They are defined by typing ``String``.
