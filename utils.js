
export function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  // The maximum is inclusive and the minimum is inclusive
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
export function getRandomOperator() {
  const operators = ['+', '-', '*', '/'];
  const randomIndex = Math.floor(Math.random() * operators.length);
  return operators[randomIndex];
}

