import { Category } from "./category";

export interface Post {
  id: number;
  title: string;
  author: string;
  content: string;
  categoryId: number;
  createdDate: string;
  codeSnippet: string;
  image: string;
  category: Category;
}