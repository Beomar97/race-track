% { shopt -s nullglob; for file in ./sec_*.tex; do echo "\\input{$file}"; done; }
