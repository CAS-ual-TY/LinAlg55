## About

This program computes a matrix x vector multiplication only by eliminating prime factors of fractions instead of doing actual arithmetic which should decrease the amount of floating point (rounding/inaccuracy) errors. See  `src/main` for the actual code of matrix and fraction arithmetic and data structures, `src/test` for some junit tests (they are not complete), `src/assignment` for the actual assignment.

### Implementation
`SpecialLinkedList` is an constantly sorted 1-way linked list. It contains some utility methods to specifically support what it is used for in this repo. All methods run in linear time. It is used for prime factors of fractions.

`Fraction` contains 2 instances of this list: One for the numerator and one for the denominator of the fraction. Contains basic arithmetic and utility methods for finding and eliminating shared or missing prime factors in different lists.

`Matrix` Square matrix implementation. Contains needed arithmetic for the assignments.

## Assignments

The assignment goes as follows:

    let
        [ 10   7    8    7 ]       [ 32 ]       [ x1 ]
        [  7   5    6    5 ]       [ 23 ]       [ x2 ]
    A = [  8   6   10    9 ]   b = [ 33 ]   x = [ x3 ]
        [  7   5    9   10 ]       [ 31 ]       [ x4 ]
    
     with   Ax=b
So we have to compute `x` here. We do that by using the inverse of `A`:

    A-1Ax=A-1b   <=>   x=bA-1   with A-1 being the inverse of A   (AA-1=E)
Now the catch is, that there are some "measurement errors". So we have to compute 3 different solutions:

### Assignment 53

    [ 10   7    8    7 ]   [ x1 ]   =   [ 32 ]
    [  7   5    6    5 ]   [ x2 ]   =   [ 23 ]
    [  8   6   10    9 ]   [ x3 ]   =   [ 33 ]
    [  7   5    9   10 ]   [ x4 ]   =   [ 31 ]

### Assignment 54

    [ 10   7    8    7 ]   [ x1 ]   =   [ 32.1 ]
    [  7   5    6    5 ]   [ x2 ]   =   [ 22.9 ]
    [  8   6   10    9 ]   [ x3 ]   =   [ 33.1 ]
    [  7   5    9   10 ]   [ x4 ]   =   [ 30.9 ]

### Assignment 55

    [ 10       7       8.1     7.2  ]   [ x1 ]   =   [ 32 ]
    [  7.08    5.04    6       5    ]   [ x2 ]   =   [ 23 ]
    [  8       5.98    9.89    9    ]   [ x3 ]   =   [ 33 ]
    [  6.99    4.99    9       9.98 ]   [ x4 ]   =   [ 31 ]

## Output & Results

    A =
    (  10/1   7/1   8/1   7/1
       7/1   5/1   6/1   5/1
       8/1   6/1   10/1   9/1
       7/1   5/1   9/1   10/1  )
    
    Inverse of A =
    (  25/1   -41/1   10/1   -6/1
       -41/1   68/1   -17/1   10/1
       10/1   -17/1   5/1   -3/1
       -6/1   10/1   -3/1   2/1  )
    
    b =
    (  32/1   23/1   33/1   31/1  )
    
    --- Assignment 53 ---
    x =
    (  1/1   1/1   1/1   1/1  )
      =
    (  1.0   1.0   1.0   1.0  )
    
    --- Assignment 54 ---
    b =
    (  321/10   229/10   331/10   309/10  )
    
    x =
    (  46/5   -63/5   9/2   -11/10  )
      =
    (  9.2   -12.6   4.5   -1.1  )
    
    --- Assignment 55 ---
    A =
    (  1000/100   700/100   810/100   720/100
       708/100   504/100   600/100   500/100
       800/100   598/100   989/100   900/100
       699/100   499/100   900/100   998/100  )
      =
    (  10.0   7.0   8.1   7.2
       7.08   5.04   6.0   5.0
       8.0   5.98   9.89   9.0
       6.99   4.99   9.0   9.98  )
    
    Inverse of A =
    (  5735297/4616   -4556405/2308   1231935/2308   -224260/577
       -9524019/4616   7568135/2308   -2047245/2308   372520/577
       1230779/2308   -978535/1154   265645/1154   -96640/577
       -1474839/4616   1172135/2308   -318345/2308   58020/577  )
      =
    (  1242.4820190641249   -1974.1789428076256   533.7673310225304   -388.66551126516464
       -2063.262348353553   3279.087954939341   -887.0212305025997   645.6152512998267
       533.2664644714038   -847.9506065857886   230.1949740034662   -167.48700173310226
       -319.505849220104   507.85745233968805   -137.93110918544195   100.55459272097053  )
    
    x =
    (  -81.0   137.0   -34.0   22.0  )
      =
    (  -81/1   137/1   -34/1   22/1  )

### Control
I used Apache Commons Math 3.6.1 to check if the results have been correct. They are indeed correct.

    --- Assignment 53 ---
    x = {{1.0},{1.0},{1.0},{1.0}}
    --- Assignment 54 ---
    x = {{9.2},{-12.6},{4.5},{-1.1}}
    --- Assignment 55 ---
    x = {{-81.0},{137.0},{-34.0},{22.0}}

## Future
Not planning to do anything with this. There is a bunch of improvements that can be done when it comes to general code repetition and other things.
