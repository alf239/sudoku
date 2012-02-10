Ready to apply?
===============

Excellent! Impress us by solving the following challenge.

Write a standard Sudoku solver in your favorite language. Once you've done that, modify it to also solve a 4x4 Super Sudoku puzzle (hex instead of just 1-9).

Here's some PHP code to get you started: (use any language you like)

```php
/**
 * Simple class representing a sudoku puzzle
 */
class Sudoku {

	// Array representing our puzzle
	private $puzzle = array();

	/**
	 * Parses sudoku puzzle file and builds data structure for solving.
	 * @param  string $file path to file containing sudoku puzzle
	 */
	public function __construct($file) {

	}

	/**
	 * Solves sudoku puzzle
	 */
	public function solve() {

	}

	/**
	 * You may want or need to create more methods for this class.
	 */

	/**
	 * Returns string representation of sudoku puzzle for printing
	 * @return string
	 */
	public function __toString() {
		return '';
	}

}

if ($argc != 2 || in_array($argv[1], array('--help', '-help', '-h', '-?'))) {
	print "Usage sudoku.php \n"; exit;
}

$file = $argv[1];

$sudoku = new Sudoku($file);
$sudoku->solve();
print $sudoku;
```

Here's a sample test case:

```
9____3_6_
_6_____12
2_518__3_
6__5___2_
1_96_83_7
_5___2__9
_3__641_8
71_____9_
_9_2____3
```

