{{- define "microservice.deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include (printf "%s.fullname" .Chart.Name) . }}
  labels:
    {{- include (printf "%s.labels" .Chart.Name) . | nindent 4 }}
spec:
  template:
    spec:
      containers:
        - name: {{ .Chart.Name }}
          env:
            {{- include "microservice.commonEnv" . | nindent 12 }}
            {{- include (printf "%s.specificEnv" .Chart.Name) . | nindent 12 }}
{{- end -}}