# operator-adder

Takes values from devices with same service and adds them together returning the sum of all last messages (see example). Sets the timestamp to the one from the last message

## Differences on this branch

The main branches only process one value of each device. This branch processes three values per device. 

## Inputs

* value1 (float): Reading from device
* value2 (float): Reading from device
* value3 (float): Reading from device
* timestamp (string): Timestamp from device

## Outputs

* sum (float): Sum of all latest values
* lastTimestamp (string): timestamp of last message

## Example


| Device | value1 | value2 | value3 | timestamp             | sum | lastTimestamp          |
|--------|--------|--------|--------|-----------------------|-----|------------------------|
| A      | 1      | 2      | 3      |2000-01-01T10:25:13+02 | 8   | 2000-01-01T10:25:13+02 |
| B      | 5      | 6      | 7      |2000-01-01T18:37:11+04 | 24  | 2000-01-01T18:37:11+04 |
| A      | -1     | -2     | -5     |2001-03-04T12:14:54+02 | 10  | 2001-03-04T12:14:54+02 |
