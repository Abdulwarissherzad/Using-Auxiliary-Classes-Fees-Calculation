package model;

import java.io.Serializable;

/*
 * Document.java
 * @author Abdul Waris Sherzad
 */

public class Document implements Comparable<Document>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String topic;
	private String title;
	private String body;
	
	public Document() {
		super();
	}
	
	public Document(String date, String topic, String title, String body) {
		this.date  = date;
		this.topic = topic;
		this.title = title;
		this.body  = body;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Document [date=" + date + ", topic=" + topic + ", title=" + title + ", body=" + body + "]";
	}

	@Override
	public int compareTo(Document document) {
		return this.topic.compareTo(document.getTopic());
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Document) {
        	Document document = (Document) obj;
            if (this.topic.equals(document.getTopic())) {
                return true;
            }
        }
        return false;
    }
 
	@Override
    public int hashCode() {
        return this.topic.hashCode();
    }
}
