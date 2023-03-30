package webhook

type Component struct {
	Timestamp      string `json:"timestamp"`
	NodeId         string `json:"nodeId"`
	Initiator      string `json:"initiator"`
	RepositoryName string `json:"repositoryName"`
	Action         string `json:"action"`
	Component      struct {
		Id     string `json:"id"`
		Format string `json:"format"`
		Name   string `json:"name"`
		Group  string `json:"group"`
	}
}
