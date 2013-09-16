package es.us.isa.bpms.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Set;

/**
 * User: resinas
 * Date: 09/04/13
 * Time: 08:54
 */
//@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
//        include=JsonTypeInfo.As.PROPERTY, property="kind")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties
public class ModelInfo {

    private String modelId;
    private String name;
    private String url;
    private String editor;
    private String description;
    private String cloneFrom;
    private Set<String> shared;
    private boolean owner;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public ModelInfo() {

    }

    public ModelInfo(String modelId, String url) {
        super();
        this.modelId = modelId;
        this.name = modelId;
        this.url = url;
    }

    public ModelInfo(String modelId, String url, String editor) {
        this(modelId,url);
        this.editor = editor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCloneFrom() {
        return cloneFrom;
    }

    public void setCloneFrom(String cloneFrom) {
        this.cloneFrom = cloneFrom;
    }

    public boolean hasClone() {
        return cloneFrom != null && !"".equals(cloneFrom);
    }

    public Set<String> getShared() {
        return shared;
    }

    public void setShared(Set<String> shared) {
        this.shared = shared;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}

