export class Suggestions {
  label: string;
  items: string[];

  constructor(label: string, items: string[]) {
    this.label = label;
    this.items = items;
  }

  remove(item: string):void {
    const index = this.items.indexOf(item);
    if (index <= -1) {
      console.error("Suggestion not found in items", item, JSON.stringify(this.items));
      return;
    }

    this.items.splice(index, 1);
  }
}
