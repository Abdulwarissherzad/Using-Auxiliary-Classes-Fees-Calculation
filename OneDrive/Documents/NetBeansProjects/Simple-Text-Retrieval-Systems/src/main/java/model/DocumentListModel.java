package model;

import java.util.List;

import javax.swing.AbstractListModel;

/*
 * DocumentListModel.java
 * 
 * @author Abdul Waris Sherzad
 */
@SuppressWarnings("hiding")
public class DocumentListModel<Document> extends AbstractListModel<Document> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<Document> list;

	public DocumentListModel(List<Document> list) {
		this.list = list;
	}

	public void addDocument(Document document) {
		list.add(document);
		int index = list.size();
		fireContentsChanged(document, index, index);
	}

	public void fireDataChanged() {
		int index = list.size();
		fireContentsChanged(list.get(index - 1), index, index);
	}

	public int getSize() {
		return list.size();
	}

	@Override
	public Document getElementAt(int index) {
		return (Document)list.get(index);
	}
}
