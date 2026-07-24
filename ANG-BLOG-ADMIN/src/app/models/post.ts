export interface BlogPost {
  id?: number;
  title: string;
  author: string;
  content: string;
  categoryId: number;
  createdDate: string;
  codeSnippet: string;
  image: string;
}